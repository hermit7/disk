package com.hadoop.hbaseTest.util;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class HbaseConn {

	private static Configuration conf = null;
	private static Connection conn = null;
	private static Admin admin = null;
	static {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.137.30");
		conf.set("hbase.zookeeper.property.clientPort", "2182");
		conf.set("hbase.rootdir", "hdfs://192.168.137.30:9000/hbase");
		try {
			conn = ConnectionFactory.createConnection(conf);
			admin = conn.getAdmin();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getConn() {
		return conn;
	}

	public static Configuration getConf() {
		return conf;
	}

	public static Admin getAdmin() {
		return admin;
	}

	public static void main(String[] args) {
		System.out.println(HbaseConn.getAdmin());
	}
}
