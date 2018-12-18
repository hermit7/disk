package com.ise.dao;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.Filter;

public interface HBaseDao {
	public long incrCounter(String tableName, String rowKey, String family, String column, long range);

	public boolean updateOneData(String tableName, String rowKey, String family, String column, String value);

	public ResultScanner getResultScannerByFilter(String tableName, Filter filter);

	public boolean deleteDataByRow(String tableName, String rowKey);

	public boolean deleteData(String tableName,Delete delete);
	
	public boolean updateMoreData(String tableName, String rowKey, String family, String[] column, String[] value);
	
	public boolean recordExisted(String tableName, Filter filter);
	
	public boolean recordExisted(String tableName, String rowKey);
	
	public Result getResultByRow(String tableName, String rowKey);
}
