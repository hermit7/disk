package com.ise.dao.impl;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import com.ise.dao.HBaseDao;
import com.ise.dao.conn.HBaseConn;

@Repository("hbaseDao")
public class HBaseDaoImpl implements HBaseDao {

	private Connection conn = HBaseConn.getConn();

	public static void main(String[] args) {
	}

	public long incrCounter(String tableName, String rowKey, String family, String column, long range) {
		long count = 0l;
		try {
			Table table = conn.getTable(TableName.valueOf(tableName));
			count = table.incrementColumnValue(Bytes.toBytes(rowKey), Bytes.toBytes(family), Bytes.toBytes(column),
					range);
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public boolean updateOneData(String tableName, String rowKey, String family, String column, String value) {
		boolean flag = true;
		try {
			Table table = conn.getTable(TableName.valueOf(tableName));
			Put put = new Put(Bytes.toBytes(rowKey));
			put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
			table.put(put);
			table.close();
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean updateMoreData(String tableName, String rowKey, String family, String[] column, String[] value) {
		boolean flag = true;
		try {
			Table table = conn.getTable(TableName.valueOf(tableName));
			Put put = new Put(Bytes.toBytes(rowKey.toString()));
			for (int i = 0; i < column.length; i++) {
				put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column[i]), Bytes.toBytes(value[i]));
			}
			table.put(put);
			table.close();
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public ResultScanner getResultScannerByFilter(String tableName, Filter filter) {
		ResultScanner resultScanner = null;
		try {
			Table table = conn.getTable(TableName.valueOf(tableName));
			Scan scan = new Scan();
			if (filter != null) {
				scan.setFilter(filter);
			}
			resultScanner = table.getScanner(scan);
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultScanner;
	}

	@Override
	public boolean deleteDataByRow(String tableName, String rowKey) {
		boolean flag = true;
		try {
			Table table = conn.getTable(TableName.valueOf(tableName));
			Delete deleteAll = new Delete(Bytes.toBytes(rowKey));
			table.delete(deleteAll);
			table.close();
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	@Override
	public boolean deleteData(String tableName,Delete delete) {
		boolean flag = true;
		try {
			Table table = conn.getTable(TableName.valueOf(tableName));
			table.delete(delete);
			table.close();
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	@Override
	public boolean recordExisted(String tableName, Filter filter) {
		try {
			ResultScanner rs = getResultScannerByFilter(tableName, filter);
			Iterator<Result> iter = rs.iterator();
			return iter.hasNext();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean recordExisted(String tableName, String rowKey) {
		Get get = new Get(Bytes.toBytes(rowKey));
		get.setCheckExistenceOnly(true);
		try {
			Table table = conn.getTable(TableName.valueOf(tableName));
			Result result = table.get(get);
			if (result.size() == 0) {
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	 /**
     * 获得一行数据
     * @category get 'tableName','rowKey'
     * @param tableName
     * @param rowKey
     * @return Result||null
     * @throws IOException
     */
    public Result getResultByRow(String tableName, String rowKey) {
    	Result result = null;
    	try {
	        Table table = conn.getTable(TableName.valueOf(tableName));
	        Get get = new Get(Bytes.toBytes(rowKey));
	        result = table.get(get);
	        table.close();
    	} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
    }

}