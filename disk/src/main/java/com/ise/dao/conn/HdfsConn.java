package com.ise.dao.conn;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

public class HdfsConn {
	private FileSystem fs = null;
	private Configuration conf = null;
	
	private static class SingletonHolder {
		private static final HdfsConn INSTANCE = new HdfsConn();
	}

	private HdfsConn() {
		try {
			conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://192.168.137.30:9000/");
			fs = FileSystem.get(new URI("hdfs://192.168.137.30:9000/"), conf, "root" );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static FileSystem getFileSystem() {
		return SingletonHolder.INSTANCE.fs;
	}
	
	public static Configuration getConfiguration() {
		return SingletonHolder.INSTANCE.conf;
	}
	
	public static void main(String[] args) {
		System.out.println(getFileSystem());
	}
}
