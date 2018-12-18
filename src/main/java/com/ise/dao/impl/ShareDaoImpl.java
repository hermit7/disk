package com.ise.dao.impl;

import java.util.Date;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ise.constant.Constants;
import com.ise.dao.HBaseDao;
import com.ise.dao.HdfsDao;
import com.ise.dao.ShareDao;
import com.ise.pojo.HFile;
import com.ise.pojo.User;
import com.ise.util.GeneralUtil;

@Repository("shareDao")
public class ShareDaoImpl implements ShareDao {

	@Autowired
	private HBaseDao hbaseDao;

	@Autowired
	private HdfsDao hdfsDao;

	/**
	 * 添加一个分享记录
	 */
	@Override
	public boolean addShare(User user, String path, String receiverId, String receiverName) {
		boolean exist = hdfsDao.isPathExist(path);
		if (exist) {
			HFile file = hdfsDao.getFileStatus(path);
			long rowKey = hbaseDao.incrCounter(Constants.TABLE_GID, Constants.ROWKEY_GID, Constants.FAMILY_GID,
					Constants.COLUMN_GID, 1);
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMN[0], String.valueOf(rowKey));
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMN[1], "public");
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMN[2], GeneralUtil.formatDate(new Date()));
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMN[3], path);
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMN[4], file.getName());
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMN[5], file.getType());
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMN[6], file.getSize());
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMN[7], receiverId);
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMN[8], receiverName);
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMN[9], user.getUserId());
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMN[10], user.getUsername());
			return true;
		}
		return false;
	}

	@Override
	public boolean addGroupShare(User user, String path, String groupNumber, String groupName) {
		boolean exist = hdfsDao.isPathExist(path);
		if (exist) {
			HFile file = hdfsDao.getFileStatus(path);
			long rowKey = hbaseDao.incrCounter(Constants.TABLE_GID, Constants.ROWKEY_GID, Constants.FAMILY_GID,
					Constants.COLUMN_GID, 1);
			hbaseDao.updateOneData(Constants.GROUP_SHARE_TABLE, groupNumber + "_" + rowKey,
					Constants.GROUP_SHARE_FAMILY, Constants.GROUP_SHARE_COLUMN[0], String.valueOf(rowKey));
			hbaseDao.updateOneData(Constants.GROUP_SHARE_TABLE, groupNumber + "_" + rowKey,
					Constants.GROUP_SHARE_FAMILY, Constants.GROUP_SHARE_COLUMN[1], groupName);
			hbaseDao.updateOneData(Constants.GROUP_SHARE_TABLE, groupNumber + "_" + rowKey,
					Constants.GROUP_SHARE_FAMILY, Constants.GROUP_SHARE_COLUMN[2], groupNumber);
			hbaseDao.updateOneData(Constants.GROUP_SHARE_TABLE, groupNumber + "_" + rowKey,
					Constants.GROUP_SHARE_FAMILY, Constants.GROUP_SHARE_COLUMN[3], GeneralUtil.formatDate(new Date()));
			hbaseDao.updateOneData(Constants.GROUP_SHARE_TABLE, groupNumber + "_" + +rowKey,
					Constants.GROUP_SHARE_FAMILY, Constants.GROUP_SHARE_COLUMN[4], path);
			hbaseDao.updateOneData(Constants.GROUP_SHARE_TABLE, groupNumber + "_" + +rowKey,
					Constants.GROUP_SHARE_FAMILY, Constants.GROUP_SHARE_COLUMN[5], file.getName());
			hbaseDao.updateOneData(Constants.GROUP_SHARE_TABLE, groupNumber + "_" + rowKey,
					Constants.GROUP_SHARE_FAMILY, Constants.GROUP_SHARE_COLUMN[6], file.getType());
			hbaseDao.updateOneData(Constants.GROUP_SHARE_TABLE, groupNumber + "_" + rowKey,
					Constants.GROUP_SHARE_FAMILY, Constants.GROUP_SHARE_COLUMN[7], file.getSize());
			hbaseDao.updateOneData(Constants.GROUP_SHARE_TABLE, groupNumber + "_" + rowKey,
					Constants.GROUP_SHARE_FAMILY, Constants.GROUP_SHARE_COLUMN[8], user.getUserId());
			hbaseDao.updateOneData(Constants.GROUP_SHARE_TABLE, groupNumber + "_" + rowKey,
					Constants.GROUP_SHARE_FAMILY, Constants.GROUP_SHARE_COLUMN[9], user.getUsername());
			return true;
		}
		return false;
	}

	/**
	 * 以一定规则扫描share表
	 */
	@Override
	public ResultScanner getResultScannerByFilter(String tableName, Filter filter) {
		return hbaseDao.getResultScannerByFilter(tableName, filter);
	}

	/**
	 * 删除我的分享
	 */
	@Override
	public boolean deleteShare(String tableName, String rowKey) {
		return hbaseDao.deleteDataByRow(tableName, rowKey);
	}

}
