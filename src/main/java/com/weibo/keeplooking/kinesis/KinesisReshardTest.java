package com.weibo.keeplooking.kinesis;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.model.DescribeStreamRequest;
import com.amazonaws.services.kinesis.model.DescribeStreamResult;
import com.amazonaws.services.kinesis.model.MergeShardsRequest;
import com.amazonaws.services.kinesis.model.ResourceNotFoundException;
import com.amazonaws.services.kinesis.model.Shard;
import com.amazonaws.services.kinesis.model.SplitShardRequest;
import com.amazonaws.services.kinesis.model.StreamDescription;

/**
 * Kinesis shards spliting and merging.
 * 
 * @author Johnny
 */
public class KinesisReshardTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(KinesisReshardTest.class);

    private AmazonKinesis kinesisClient;
    private String streamName = "KkTest";

    @Before
    public void setup() throws Exception {
        AWSCredentials credentials = new ProfileCredentialsProvider("default").getCredentials();

        final ClientConfiguration config = new ClientConfiguration();
        final StringBuilder userAgent = new StringBuilder(ClientConfiguration.DEFAULT_USER_AGENT);
        userAgent.append(" ");
        userAgent.append("amazon-kinesis-test");
        userAgent.append("/");
        userAgent.append("1.0.0");
        config.setUserAgent(userAgent.toString());

        kinesisClient = new AmazonKinesisClient(credentials, config);

        String regionName = "us-west-2";
        Region region = RegionUtils.getRegion(regionName);
        kinesisClient.setRegion(region);
    }

    @Test
    public void describeStream() {
        StreamDescription discription = kinesisClient.describeStream(streamName).getStreamDescription();
        LOGGER.info("Stream:{}, Status:{}", streamName, discription.getStreamStatus());
        for (Shard shard : discription.getShards()) {
            LOGGER.info("Shard {}: {} - {}, Parent:{}", shard.getShardId(), shard.getHashKeyRange().getStartingHashKey(), shard.getHashKeyRange()
                    .getEndingHashKey(),
                    shard.getParentShardId());
        }

        List<Shard> orderedMergableShards = getOrderedMergableShards(discription.getShards());
        for (Shard shard : orderedMergableShards) {
            LOGGER.info("Mergable shard {}: {} - {}, Parent:{}", shard.getShardId(), shard.getHashKeyRange().getStartingHashKey(), shard.getHashKeyRange()
                    .getEndingHashKey(),
                    shard.getParentShardId());
        }
    }

    private List<Shard> getOrderedMergableShards(List<Shard> allShards) {
        return allShards.stream().filter(shard -> shard.getSequenceNumberRange().getEndingSequenceNumber() == null)
                .sorted((shard1, shard2) -> new BigInteger(shard1.getHashKeyRange().getEndingHashKey()).compareTo(new BigInteger(shard2.getHashKeyRange()
                        .getEndingHashKey()))).collect(Collectors.toList());
    }

    @Test
    public void splitShard() {
        describeStream();

        DescribeStreamResult discription = kinesisClient.describeStream(streamName);
        List<Shard> shards = discription.getStreamDescription().getShards();
        Shard toSplit = getOrderedMergableShards(shards).get(0);

        SplitShardRequest splitShardRequest = new SplitShardRequest();
        splitShardRequest.setStreamName(streamName);
        splitShardRequest.setShardToSplit(toSplit.getShardId());
        BigInteger startingHashKey = new BigInteger(toSplit.getHashKeyRange().getStartingHashKey());
        BigInteger endingHashKey = new BigInteger(toSplit.getHashKeyRange().getEndingHashKey());
        String newStartingHashKey = startingHashKey.add(endingHashKey).divide(new BigInteger("2")).toString();
        splitShardRequest.setNewStartingHashKey(newStartingHashKey);
        kinesisClient.splitShard(splitShardRequest);
        LOGGER.info("Splitting shard {}, [{}-{}] -> [{}-{}], [{}-{}]",
                toSplit.getShardId(), startingHashKey, endingHashKey, startingHashKey, newStartingHashKey, newStartingHashKey, endingHashKey);

        waitStreamActive();
        LOGGER.info("Splitting done.");

        describeStream();
    }

    @Test
    public void mergeShards() {
        describeStream();

        DescribeStreamResult discription = kinesisClient.describeStream(streamName);
        List<Shard> orderedMergableShards = this.getOrderedMergableShards(discription.getStreamDescription().getShards());
        Shard shard1 = orderedMergableShards.get(0);
        Shard shard2 = orderedMergableShards.get(1);

        MergeShardsRequest mergeShardsRequest = new MergeShardsRequest();
        mergeShardsRequest.setStreamName(streamName);
        mergeShardsRequest.setShardToMerge(shard1.getShardId());
        mergeShardsRequest.setAdjacentShardToMerge(shard2.getShardId());
        kinesisClient.mergeShards(mergeShardsRequest);
        LOGGER.info("Merging shards: {}[{}-{}] and {}[{}-{}]",
                shard1, shard1.getHashKeyRange().getStartingHashKey(), shard1.getHashKeyRange().getEndingHashKey(),
                shard2, shard2.getHashKeyRange().getStartingHashKey(), shard2.getHashKeyRange().getEndingHashKey());

        waitStreamActive();
        LOGGER.info("Merging done.");

        describeStream();
    }

    private void waitStreamActive() {
        DescribeStreamRequest describeStreamRequest = new DescribeStreamRequest();
        describeStreamRequest.setStreamName(streamName);

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (10 * 60 * 1000);
        while (System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(20 * 1000);
            } catch (Exception e) {
            }
            try {
                DescribeStreamResult describeStreamResponse = kinesisClient.describeStream(describeStreamRequest);
                String streamStatus = describeStreamResponse.getStreamDescription().getStreamStatus();
                if (streamStatus.equals("ACTIVE")) {
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            } catch (ResourceNotFoundException e) {
            }
        }
        if (System.currentTimeMillis() >= endTime) {
            throw new RuntimeException("Stream " + streamName + " never went active");
        }
    }

}
