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
					Constants.SHARE_COLUMEN[0], rowKey);
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMEN[1], "public");
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMEN[2], new Date().toString());
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMEN[3], path);
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMEN[4], file.getName());
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMEN[5], file.getType());
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMEN[6], file.getSize());
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMEN[7], receiverId);
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMEN[8], receiverName);
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMEN[9], user.getUserId());
			hbaseDao.updateOneData(Constants.SHARE_TABLE, user.getUserId() + "_" + rowKey, Constants.SHARE_FAMILY,
					Constants.SHARE_COLUMEN[10], user.getUsername());
			return true;
		}
		return false;
	}

	/**
	 * 以一定规则扫描share表
	 */
	@Override
	public ResultScanner getResultScannerByShare(Filter filter) {
		return hbaseDao.getResultScannerByFilter(Constants.SHARE_TABLE, filter);
	}

	/**
	 * 删除我的分享
	 */
	@Override
	public boolean deleteShare(String rowKey) {
		return hbaseDao.deleteDataByRow(Constants.SHARE_TABLE, rowKey);
	}

}
