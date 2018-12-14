package com.ise.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ise.constant.Constants;
import com.ise.dao.HBaseDao;
import com.ise.dao.RelationDao;
import com.ise.pojo.User;

@Repository("relationDao")
public class RelationDaoImpl implements RelationDao {

	@Autowired
	private HBaseDao hbaseDao;

	/**
	 * 展示关注对象的所有信息
	 */
	@Override
	public List<User> listFriends(User user) {
		Filter filter = new PrefixFilter(Bytes.toBytes(user.getUserId() + "_"));
		ResultScanner rs = hbaseDao.getResultScannerByFilter(Constants.FRIEND_TABLE, filter);
		List<User> list = new ArrayList<>();
		User friend = null;
		Iterator<Result> iter = rs.iterator();
		while (iter.hasNext()) {
			friend = new User();
			Result result = iter.next();
			String userId = Bytes.toString(
					result.getValue(Bytes.toBytes(Constants.FRIEND_FAMILY), Bytes.toBytes(Constants.FRIEND_COLUMN[0])));
			String username = Bytes.toString(
					result.getValue(Bytes.toBytes(Constants.FRIEND_FAMILY), Bytes.toBytes(Constants.FRIEND_COLUMN[1])));
			String nickname = Bytes.toString(
					result.getValue(Bytes.toBytes(Constants.FRIEND_FAMILY), Bytes.toBytes(Constants.FRIEND_COLUMN[2])));
			friend.setUserId(userId);
			friend.setUsername(username);
			friend.setNickname(nickname);
			list.add(friend);
		}
		return list;
	}

	/**
	 * 显示关注对象名称，和id
	 */
	@Override
	public List<Map<String, String>> showFollows(User user) {
		Filter filter = new PrefixFilter(Bytes.toBytes(user.getUserId() + "_"));
		ResultScanner rs = hbaseDao.getResultScannerByFilter(Constants.FRIEND_TABLE, filter);
		List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> map = null;
		Iterator<Result> iter = rs.iterator();
		while (iter.hasNext()) {
			map = new HashMap<>();
			Result result = iter.next();
			String userId = Bytes.toString(
					result.getValue(Bytes.toBytes(Constants.FRIEND_FAMILY), Bytes.toBytes(Constants.FRIEND_COLUMN[0])));
			String username = Bytes.toString(
					result.getValue(Bytes.toBytes(Constants.FRIEND_FAMILY), Bytes.toBytes(Constants.FRIEND_COLUMN[1])));
			map.put("id", userId);
			map.put("text", username);
			list.add(map);
		}
		return list;
	}

	/**
	 * 对关注对象备注
	 */
	@Override
	public boolean remarkFriend(String userId, String friendId, String remark) {
		return hbaseDao.updateOneData(Constants.FRIEND_TABLE, userId + "_" + friendId, Constants.FRIEND_FAMILY,
				Constants.FRIEND_COLUMN[2], remark);
	}

	/**
	 * 删除关注
	 */
	@Override
	public boolean deleteFriend(String userId, String friendId) {
		return hbaseDao.deleteDataByRow(Constants.FRIEND_TABLE, userId + "_" + friendId);
	}

	/**
	 * 关注某个用户
	 */
	@Override
	public boolean follow(String userId, String friendId, String friendName) {
		String[] value = { friendId, friendName, "" };
		return hbaseDao.updateMoreData(Constants.FRIEND_TABLE, userId + "_" + friendId, Constants.FRIEND_FAMILY,
				Constants.FRIEND_COLUMN, value);
	}

}
