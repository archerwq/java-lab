package com.weibo.keeplooking.image;

import java.io.FileInputStream;
import java.io.InputStream;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class MetadataExtractor {

	public static void main(String[] args) throws Exception {
		InputStream is = new FileInputStream("/Volumes/Seagate Bac/image/picture/爸妈十一北京行/DSC06061.JPG");
		// InputStream is = new
		// FileInputStream("/Users/qiangwang/Desktop/WechatIMG1597.jpeg");
		Metadata metadata = ImageMetadataReader.readMetadata(is);

		for (Directory directory : metadata.getDirectories()) {
			for (Tag tag : directory.getTags()) {
				System.out.format("[%s] - %s = %s\n", directory.getName(), tag.getTagName(), tag.getDescription());
			}
			if (directory.hasErrors()) {
				for (String error : directory.getErrors()) {
					System.err.format("ERROR: %s", error);
				}
			}
		}
	}

}
