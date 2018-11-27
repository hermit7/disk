package com.ise.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Repository;

import com.ise.dao.conn.HdfsConn;

@Repository("hdfsDao")
public class HdfsDao {

	private static FileSystem fs = HdfsConn.getFileSystem();
	
	public Map<String,String> listFiles() {
		HashMap<String, String> map = new HashMap<>();
		try {
			FileStatus[] listStatus = fs.listStatus(new Path("/"));
			for (FileStatus status : listStatus) {
				String name = status.getPath().getName();
				if(status.isDirectory()) {
					map.put(name, "dir");
				}else {
					map.put(name, name.substring(name.lastIndexOf('.')));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}
