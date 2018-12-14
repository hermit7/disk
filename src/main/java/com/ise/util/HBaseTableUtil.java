package com.ise.util;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder.ModifyableTableDescriptor;

import com.ise.constant.Constants;
import com.ise.dao.conn.HBaseConn;

public class HBaseTableUtil {
	// private static Connection conn = HBaseConn.getConn();
	private static Admin admin = HBaseConn.getAdmin();

	public static void main(String[] args) {
		// dropTables();
		createTable(Constants.SHARE_TABLE, Constants.SHARE_FAMILY);
		createTable(Constants.TABLE_GID, Constants.FAMILY_GID);
	}

	// ������
	public static void createTable(String tb, String family) {
		// ������������
		dropTable(tb);
		TableName tableName = TableName.valueOf(tb);
		ModifyableTableDescriptor tableDescriptor = new TableDescriptorBuilder.ModifyableTableDescriptor(tableName);

		// ��������������
		ModifyableColumnFamilyDescriptor familyDescriptor = new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(
				toBytes(family));

		// ��������
		tableDescriptor.setColumnFamily(familyDescriptor);

		// ������
		try {
			// ��������ڣ�������
			if (!admin.tableExists(tableName)) {
				admin.createTable(tableDescriptor);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ɾ�����еı�
	public static void dropTables() {
		try {
			TableName[] names = admin.listTableNames();
			for (TableName tb : names) {
				if (admin.isTableEnabled(tb)) {
					admin.disableTable(tb);
					admin.deleteTable(tb);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ɾ��һ�ű�
	public static void dropTable(String tableName) {
		TableName tb = TableName.valueOf(tableName);
		try {
			if (admin.isTableEnabled(tb)) {
				admin.disableTable(tb);
				admin.deleteTable(tb);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
