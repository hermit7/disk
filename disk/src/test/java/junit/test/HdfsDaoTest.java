package junit.test;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

public class HdfsDaoTest{
	
	
	FileSystem fs = null;
	
	@Before
	public void init() throws Exception {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://192.168.137.30:9000/");
		fs = FileSystem.get(new URI("hdfs://192.168.137.30:9000/"), conf, "root");
	}
	
	@Test
	public void listFiles() {
		try {
			FileStatus[] listStatus = fs.listStatus(new Path("/"));
			for (FileStatus status : listStatus) {
				//showFile(status);
				System.out.println(status.getPath().getName());
			}
		}  catch (IOException e) {
			e.printStackTrace();
		}
	}
}
