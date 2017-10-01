package com.weibo.keeplooking.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

public class NewAPITest {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewAPITest.class);

    private static final long POLL_TIME_OUT = 1000L;

    private KafkaProducer<String, String> producer;
    private KafkaConsumer<String, String> consumer;

    private Random rand = new Random();

    @Before
    public void setup() {
        String kafkaServers = "127.0.0.1:9092";
        initProducer(kafkaServers);
        initConsumer(kafkaServers, "NewAPITest-Group1", 10240);
    }

    @After
    public void cleanup() {
        if (producer != null) {
            producer.close();
        }
        if (consumer != null) {
            consumer.commitAsync(); // commit offset during cleanup
            consumer.close();
        }
        LOGGER.info("cleanup done");
    }

    @Test
    public void sanityTest() throws Exception {
        String topic = "NewAPITest";

        // seek to end
        consumer.subscribe(Arrays.asList(topic));
        consumer.poll(POLL_TIME_OUT);
        consumer.seekToEnd();
        consumer.assignment().forEach(partition -> LOGGER.info(partition.toString()));

        // produce a message
        String message = produceRandomMsg(topic);

        // consume
        ConsumerRecords<String, String> records = pollRecords();
        Assert.assertEquals(1, records.count());
        Assert.assertEquals(message, records.iterator().next().value());
        consumer.commitSync();
    }

    @Test
    public void testOffsetSeek() throws Exception {
        String topic = "NewAPITest";
        consumer.subscribe(Arrays.asList(topic));

        // produce a message
        String message = produceRandomMsg(topic);

        // consume
        ConsumerRecords<String, String> records = pollRecords();
        Assert.assertEquals(1, records.count());
        Assert.assertEquals(message, records.iterator().next().value());
        consumer.commitSync();

        // produce 3 messages
        produceRandomMsg(topic);
        produceRandomMsg(topic);

        // seeking to the final offset in all partitions only when poll(long) or
        // position(TopicPartition) are called
        consumer.seekToEnd();
        produceRandomMsg(topic);
        records = pollRecords();
        Assert.assertEquals(0, records.count()); // because seekToEnd is lazy

        message = produceRandomMsg(topic);
        records = pollRecords();
        Assert.assertEquals(1, records.count());
        Assert.assertEquals(message, records.iterator().next().value());
        consumer.commitSync();
    }

    private void initProducer(String kafkaServers) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());

        producer = new KafkaProducer<String, String>(props);
    }

    private void initConsumer(String kafkaServers, String groupId, int maxFetchBytes) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxFetchBytes);

        consumer = new KafkaConsumer<String, String>(props);
    }

    /**
     * Send a random message to the specified topic synchronously.
     */
    private String produceRandomMsg(String topic) throws Exception {
        String message = Long.toString(rand.nextLong());
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, message);
        RecordMetadata result = producer.send(producerRecord).get();
        LOGGER.info(">>>>> partition: {}, offset: {}, message: {}", result.partition(), result.offset(), message);
        return message;
    }

    /**
     * Keep polling until there are messages returned.
     */
    private ConsumerRecords<String, String> pollRecords() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(POLL_TIME_OUT);
            if (records == null || records.isEmpty()) {
                LOGGER.info("no message polled, continuing...");
                continue;
            }
            records.forEach(record -> LOGGER.info("<<<<< {}", record));
            return records;
        }
    }

}

