package com.ise.dao;


import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.Filter;

import com.ise.pojo.User;

public interface ShareDao {

	boolean addShare(User user, String path, String receiveId, String receiveName);

	public ResultScanner getResultScannerByFilter(String tableName,Filter filter);

	public boolean deleteShare(String tableName,String rowKey);

	boolean addGroupShare(User user, String path, String groupNumber, String groupName);

}
