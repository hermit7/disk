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

	// 创建表
	public void createTable() {
		// 创建表描述器
		TableName tableName = TableName.valueOf("user");
		ModifyableTableDescriptor tableDescriptor = new TableDescriptorBuilder.ModifyableTableDescriptor(tableName);

		// 创建列族描述器
		ModifyableColumnFamilyDescriptor familyDescriptor = new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(
				toBytes("info"));

		// 增加列族
		tableDescriptor.setColumnFamily(familyDescriptor);

		// 创建表
		try {
			// 如果表不存在，创建表
			if (!admin.tableExists(tableName)) {
				admin.createTable(tableDescriptor);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 删除表

	// 查询
	/**
	 * 需求： 1、根据用户名查询该用户是否存在 2、根据用户名查询该用户的公开信息 3、根据群ID查询该群的所有成员
	 */
	public void queryTable() {
		TableName tb = TableName.valueOf("user");
		try {
			Table table = conn.getTable(tb);
			// 3、获取数据
			Get g = new Get(toBytes("rk001"));
			// 获取指定列簇下所有列
			g.addFamily(Bytes.toBytes("info"));
			// 获取指定列簇下指定列
			//g.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("salary"));

			// 获取结果集
			Result rs = table.get(g);

			 byte[] value = rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("friends"));
			 System.out.println(new String(value).toString());
			// byte[] value1 = rs.getValue(Bytes.toBytes("personal"),
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 修改
	/**
	 * 需求 1、用户添加/删除好友 2、用户加群/退群 3、用户被移除群 4、向群添加/移除文件
	 */

	// 插入
	/**
	 * 需求： 1、向用户表中插入一条数据 2、向群组表中插入一条数据
	 */
	public void function() {
		try {
			// 创建表描述器
			ModifyableTableDescriptor tableDescriptor = new TableDescriptorBuilder.ModifyableTableDescriptor(
					TableName.valueOf("testHbase2"));

			// 创建列族描述器
			ModifyableColumnFamilyDescriptor familyDescriptor = new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(
					toBytes("columnfamily2"));

			// 增加列族
			tableDescriptor.setColumnFamily(familyDescriptor);

			// 创建表
			admin.createTable(tableDescriptor);

			// 获取所有表明
			TableName[] tableNames = admin.listTableNames();
			TableName tb = tableNames[0];

			// 判断tb是否启用
			// 禁用表tb
			if (admin.isTableEnabled(tb)) {
				admin.disableTable(tb);
			}
			// 启用表
			if (!admin.isTableEnabled(tb)) {
				admin.enableTable(tb);
			}

			// 列信息
			ModifyableColumnFamilyDescriptor columnFamilyDescriptor = new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(
					toBytes("column12"));

			// 指定某列最大版本号
			columnFamilyDescriptor.setMaxVersions(9);
			// 指定某列当前版本及最大版本
			columnFamilyDescriptor.setVersions(1, 9);

			// 添加列簇
			admin.addColumnFamily(tb, columnFamilyDescriptor);

			// 判断表是否存在
			if (admin.tableExists(tb)) {
				// 删除表
				admin.deleteTable(tb);
			}

			for (TableName tableName : tableNames) {
				System.out.println(tableName);
			}

			// 客户端api：table
			Table table = conn.getTable(tb);

			// 1、新增列簇数据
			Put p = new Put(Bytes.toBytes("row1"));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("name"), Bytes.toBytes("raju"));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("city"), Bytes.toBytes("hyderabad"));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("designation"), Bytes.toBytes("manager"));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("salary"), Bytes.toBytes("50000"));

			table.put(p);

			// 2、更新列簇
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("city"), Bytes.toBytes("Delih"));
			table.put(p);

			// 3、获取数据
			Get g = new Get(toBytes("row1"));
			// 获取指定列簇下所有列
			g.addFamily(Bytes.toBytes("personal"));
			// 获取指定列簇下指定列
			g.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("salary"));

			// 获取结果集
			// Result rs = table.get(g);

			// byte[] value = rs.getValue(Bytes.toBytes("personal"), Bytes.toBytes("name"));

			// byte[] value1 = rs.getValue(Bytes.toBytes("personal"),
			// Bytes.toBytes("city"));

			// 4、 删除数据

			Delete d = new Delete(toBytes("row1"));
			d.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("salary"));
			table.delete(d);

			// 5、 扫描
			Scan scan = new Scan();
			// 扫描指定列
			scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("city"));

			// 扫描指定列簇
			scan.addFamily(Bytes.toBytes("personal"));

			// 扫描结果集
			ResultScanner resultScanner = table.getScanner(scan);

			// 迭代结果
			resultScanner.iterator();

			// 关闭Htable
			table.close();

			// 停止hbase
			admin.shutdown();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
