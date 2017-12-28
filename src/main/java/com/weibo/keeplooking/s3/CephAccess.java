package com.weibo.keeplooking.s3;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.util.StringUtils;

/**
 *  Ceph http://docs.ceph.com/docs/hammer/radosgw/s3/java/
 */
public class CephAccess {
	
	public static void main(String[] args) {
		String accessKey = "xxxx";
		String secretKey = "yyyy";

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 conn = new AmazonS3Client(credentials);
		conn.setEndpoint("http://10.20.144.87:7480");
		
		List<Bucket> buckets = conn.listBuckets();
		for (Bucket bucket : buckets) {
		        System.out.println(bucket.getName() + "\t" +
		                StringUtils.fromDate(bucket.getCreationDate()));
		}
	}

}
