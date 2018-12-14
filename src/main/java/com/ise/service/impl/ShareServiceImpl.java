package com.ise.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ise.constant.Constants;
import com.ise.dao.HdfsDao;
import com.ise.dao.ShareDao;
import com.ise.pojo.Share;
import com.ise.pojo.User;
import com.ise.service.ShareService;

@Service("shareService")
public class ShareServiceImpl implements ShareService {

	@Autowired
	private ShareDao shareDao;
	
	@Autowired
	private HdfsDao hdfsDao;
	
	@Override
	public boolean addShare(User user, String path, String receiverId, String receiverName) {
		return shareDao.addShare(user, path, receiverId, receiverName);
	}

	/**
	 * 获取我的分享
	 */
	@Override
	public List<Share> listProvidedShare(User user) {
		List<Share> list = new ArrayList<Share>();
		Filter filter = new PrefixFilter(Bytes.toBytes(user.getUserId() + "_"));
		ResultScanner rs = shareDao.getResultScannerByShare(filter);
		Iterator<Result> iter = rs.iterator();
		while (iter.hasNext()) {
			Result r = iter.next();
			if (!r.isEmpty()) {
				Share share = new Share();
				share.setShareId(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[0]))));
				share.setShareType(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[1]))));
				share.setShareTime(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[2]))));
				share.setFilePath(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[3]))));
				share.setFileName(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[4]))));
				share.setFileType(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[5]))));
				share.setFileSize(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[6]))));
				share.setReceiverId(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[7]))));
				share.setReceiverName(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[8]))));
				list.add(share);
			}
		}
		return list;
	}

	/**
	 * 获取我收到的分享
	 */
	@Override
	public List<Share> listReceivedShare(User user) {
		List<Share> list = new ArrayList<Share>();
		Filter filter = new SingleColumnValueFilter(Bytes.toBytes(Constants.SHARE_FAMILY),
				Bytes.toBytes(Constants.SHARE_COLUMEN[8]), CompareOperator.EQUAL, Bytes.toBytes(user.getUsername()));
		ResultScanner rs = shareDao.getResultScannerByShare(filter);
		Iterator<Result> iter = rs.iterator();
		while (iter.hasNext()) {
			Result r = iter.next();
			if (!r.isEmpty()) {
				Share share = new Share();
				share.setShareId(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[0]))));
				share.setShareType(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[1]))));
				share.setShareTime(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[2]))));
				share.setFilePath(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[3]))));
				share.setFileName(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[4]))));
				share.setFileType(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[5]))));
				share.setFileSize(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[6]))));
				share.setProviderId(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[9]))));
				share.setProviderName(Bytes.toString(
						r.getValue(Bytes.toBytes(Constants.SHARE_FAMILY), Bytes.toBytes(Constants.SHARE_COLUMEN[10]))));
				list.add(share);
			}
		}
		return list;
	}

	@Override
	public boolean deleteShare(String userId, String shareId) {
		String rowKey = userId + "_" + shareId;
		return shareDao.deleteShare(rowKey);
	}

	@Override
	public boolean saveShare(String path, String dst) {
		return hdfsDao.copyFile(path, dst);
	}
}
