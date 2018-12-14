package com.ise.dao;


import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.Filter;

import com.ise.pojo.User;

public interface ShareDao {

	boolean addShare(User user, String path, String receiveId, String receiveName);

	ResultScanner getResultScannerByShare(Filter filter);

	boolean deleteShare(String rowKey);

}
