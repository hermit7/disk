package junit.test;

import java.io.IOException;
import java.net.URI;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ise.dao.conn.HdfsConn;

public class HdfsTest {

	FileSystem fs = null;
	Configuration conf = null;

	@Before
	public void init() throws Exception {
		conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://192.168.137.30:9000/");
		fs = FileSystem.get(new URI("hdfs://192.168.137.30:9000/"), conf, "root");
	}

	@Test
	public void listFiles() {
		JsonArray array = new JsonArray();
		DecimalFormat df = new DecimalFormat(".00");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			FileStatus[] listStatus = fs.listStatus(new Path("hdfs://192.168.137.30:9000/"));
			for (FileStatus file : listStatus) {
				JsonObject tmp = new JsonObject();
				System.out.println("ÎÄ¼þÂ·¾¶" + file.getPath());
				String name = file.getPath().getName();
				if (file.isDirectory()) {
					tmp.addProperty(name, "dir");
					tmp.addProperty("size", "-");
				} else {
					tmp.addProperty(name, name.substring(name.lastIndexOf('.')));
					tmp.addProperty("size", df.format(file.getLen() / Math.pow(2, 20)) + "MB");
				}
				tmp.addProperty("modificationtime", sdf.format(new Date(file.getModificationTime())));
				array.add(tmp);
			}
			System.out.println(array.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testPath() {
		Path path = new Path("d://" + "aa");
		System.out.println(path.toString());
	}

	@Test
	public void testDown() {
		try {
			fs.copyToLocalFile(new Path("hdfs://192.168.137.30:9000/aaa"), new Path("d://tmp/"));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testMove() throws IOException {
		Path src = new Path("/a");
		Path dst = new Path("/test23");
		FileUtil.copy(HdfsConn.getFileSystem(), src, HdfsConn.getFileSystem(), dst, true, conf);
	}
}
