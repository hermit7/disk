package com.ise.dao;

import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.Filter;

public interface HBaseDao {
	public long incrCounter(String tableName, String rowKey, String family, String column, long range);

	public boolean updateOneData(String tableName, Object rowKey, String family, Object column, Object value);

	public ResultScanner getResultScannerByFilter(String tableName, Filter filter);

	public boolean deleteDataByRow(String tableName, Object rowKey);

	public boolean updateMoreData(String tableName, Object rowKey, String family, String[] column, String[] value);
}
