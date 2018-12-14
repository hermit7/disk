package junit.test;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.junit.Before;
import org.junit.Test;

public class HbaseTest {

	private Configuration configuration = null;
	private Connection connection = null;
	@Before
	public void init() {
		configuration = HBaseConfiguration.create();
    	configuration.set("hbase.zookeeper.quorum", "192.168.137.30");
    	configuration.set("hbase.zookeeper.property.clientPort", "2182");
    	configuration.set("hbase.rootdir", "hdfs://192.168.137.30:9000/hbase");
    	try {
			connection = ConnectionFactory.createConnection(configuration);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInit() {
		System.out.println(connection);
	}
	
	@Test
	public void testCreate() {
		
	}
}
