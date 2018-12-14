package com.ise.dao.impl;

import java.io.IOException;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import com.ise.constant.Constants;
import com.ise.dao.HBaseDao;
import com.ise.dao.conn.HBaseConn;
import com.ise.pojo.User;

@Repository("hbaseDao")
public class HBaseDaoImpl implements HBaseDao {

	private Connection conn = HBaseConn.getConn();

	public static void main(String[] args) {
		HBaseDao impl = new HBaseDaoImpl();
		User user = new User();
		user.setUserId("1");
		user.setUsername("����");
		user.setNickname("da's��ɪ��");
		User friend = new User();
		friend.setUserId("2");
		friend.setUsername("����Ӣ");
		friend.setNickname("С�ɰ�");
		impl.updateMoreData(Constants.FRIEND_TABLE, user.getUserId() + "_" + friend.getUserId(),
				Constants.FRIEND_FAMILY, Constants.FRIEND_COLUMN,
				new String[] { friend.getUserId(), friend.getUsername(), friend.getNickname() });
		impl.updateMoreData(Constants.FRIEND_TABLE, friend.getUserId() + "_" + user.getUserId(),
				Constants.FRIEND_FAMILY, Constants.FRIEND_COLUMN,
				new String[] { user.getUserId(), user.getUsername(), user.getNickname() });
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
	public boolean updateOneData(String tableName, Object rowKey, String family, Object column, Object value) {
		boolean flag = true;
		try {
			Table table = conn.getTable(TableName.valueOf(tableName));
			Put put = new Put(Bytes.toBytes(rowKey.toString()));
			put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column.toString()), Bytes.toBytes(value.toString()));
			table.put(put);
			table.close();
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean updateMoreData(String tableName, Object rowKey, String family, String[] column, String[] value) {
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
	public boolean deleteDataByRow(String tableName, Object rowKey) {
		boolean flag = true;
		try {
			Table table = conn.getTable(TableName.valueOf(tableName));
			Delete deleteAll = new Delete(Bytes.toBytes(rowKey.toString()));
			table.delete(deleteAll);
			table.close();
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

}