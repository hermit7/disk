package com.hadoop.hbaseTest.dao;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder.ModifyableTableDescriptor;
import org.apache.hadoop.hbase.util.Bytes;

import com.hadoop.hbaseTest.util.HbaseConn;

public class HbaseFunction {

	private Connection conn = HbaseConn.getConn();
	private Admin admin = HbaseConn.getAdmin();

	public static void main(String[] args) {
		HbaseFunction func = new HbaseFunction();
		func.queryTable();
	}

	// ������
	public void createTable() {
		// ������������
		TableName tableName = TableName.valueOf("user");
		ModifyableTableDescriptor tableDescriptor = new TableDescriptorBuilder.ModifyableTableDescriptor(tableName);

		// ��������������
		ModifyableColumnFamilyDescriptor familyDescriptor = new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(
				toBytes("info"));

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

	// ɾ����

	// ��ѯ
	/**
	 * ���� 1�������û�����ѯ���û��Ƿ���� 2�������û�����ѯ���û��Ĺ�����Ϣ 3������ȺID��ѯ��Ⱥ�����г�Ա
	 */
	public void queryTable() {
		TableName tb = TableName.valueOf("user");
		try {
			Table table = conn.getTable(tb);
			// 3����ȡ����
			Get g = new Get(toBytes("rk001"));
			// ��ȡָ���д���������
			g.addFamily(Bytes.toBytes("info"));
			// ��ȡָ���д���ָ����
			//g.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("salary"));

			// ��ȡ�����
			Result rs = table.get(g);

			 byte[] value = rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("friends"));
			 System.out.println(new String(value).toString());
			// byte[] value1 = rs.getValue(Bytes.toBytes("personal"),
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// �޸�
	/**
	 * ���� 1���û����/ɾ������ 2���û���Ⱥ/��Ⱥ 3���û����Ƴ�Ⱥ 4����Ⱥ���/�Ƴ��ļ�
	 */

	// ����
	/**
	 * ���� 1�����û����в���һ������ 2����Ⱥ����в���һ������
	 */
	public void function() {
		try {
			// ������������
			ModifyableTableDescriptor tableDescriptor = new TableDescriptorBuilder.ModifyableTableDescriptor(
					TableName.valueOf("testHbase2"));

			// ��������������
			ModifyableColumnFamilyDescriptor familyDescriptor = new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(
					toBytes("columnfamily2"));

			// ��������
			tableDescriptor.setColumnFamily(familyDescriptor);

			// ������
			admin.createTable(tableDescriptor);

			// ��ȡ���б���
			TableName[] tableNames = admin.listTableNames();
			TableName tb = tableNames[0];

			// �ж�tb�Ƿ�����
			// ���ñ�tb
			if (admin.isTableEnabled(tb)) {
				admin.disableTable(tb);
			}
			// ���ñ�
			if (!admin.isTableEnabled(tb)) {
				admin.enableTable(tb);
			}

			// ����Ϣ
			ModifyableColumnFamilyDescriptor columnFamilyDescriptor = new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(
					toBytes("column12"));

			// ָ��ĳ�����汾��
			columnFamilyDescriptor.setMaxVersions(9);
			// ָ��ĳ�е�ǰ�汾�����汾
			columnFamilyDescriptor.setVersions(1, 9);

			// ����д�
			admin.addColumnFamily(tb, columnFamilyDescriptor);

			// �жϱ��Ƿ����
			if (admin.tableExists(tb)) {
				// ɾ����
				admin.deleteTable(tb);
			}

			for (TableName tableName : tableNames) {
				System.out.println(tableName);
			}

			// �ͻ���api��table
			Table table = conn.getTable(tb);

			// 1�������д�����
			Put p = new Put(Bytes.toBytes("row1"));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("name"), Bytes.toBytes("raju"));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("city"), Bytes.toBytes("hyderabad"));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("designation"), Bytes.toBytes("manager"));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("salary"), Bytes.toBytes("50000"));

			table.put(p);

			// 2�������д�
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("city"), Bytes.toBytes("Delih"));
			table.put(p);

			// 3����ȡ����
			Get g = new Get(toBytes("row1"));
			// ��ȡָ���д���������
			g.addFamily(Bytes.toBytes("personal"));
			// ��ȡָ���д���ָ����
			g.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("salary"));

			// ��ȡ�����
			// Result rs = table.get(g);

			// byte[] value = rs.getValue(Bytes.toBytes("personal"), Bytes.toBytes("name"));

			// byte[] value1 = rs.getValue(Bytes.toBytes("personal"),
			// Bytes.toBytes("city"));

			// 4�� ɾ������

			Delete d = new Delete(toBytes("row1"));
			d.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("salary"));
			table.delete(d);

			// 5�� ɨ��
			Scan scan = new Scan();
			// ɨ��ָ����
			scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("city"));

			// ɨ��ָ���д�
			scan.addFamily(Bytes.toBytes("personal"));

			// ɨ������
			ResultScanner resultScanner = table.getScanner(scan);

			// �������
			resultScanner.iterator();

			// �ر�Htable
			table.close();

			// ֹͣhbase
			admin.shutdown();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
