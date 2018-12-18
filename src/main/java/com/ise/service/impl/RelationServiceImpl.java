package com.ise.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ise.constant.Constants;
import com.ise.dao.RelationDao;
import com.ise.pojo.Group;
import com.ise.pojo.User;
import com.ise.service.RelationService;

@Service("relationService")
public class RelationServiceImpl implements RelationService {

	private Random random = new Random();

	@Autowired
	private RelationDao relationDao;

	@Override
	public List<User> listFriends(User user) {
		return relationDao.listFriends(user);
	}

	@Override
	public boolean remarkFriend(String userId, String friendId, String remark) {
		return relationDao.remarkFriend(userId, friendId, remark);
	}

	@Override
	public boolean deleteFriend(String userId, String friendId) {
		return relationDao.deleteFriend(userId, friendId);
	}

	@Override
	public boolean follow(String userId, String friendId, String friendName) {
		return relationDao.follow(userId, friendId, friendName);
	}

	@Override
	public List<Map<String, String>> showFollows(User user) {
		Filter filter = new PrefixFilter(Bytes.toBytes(user.getUserId() + "_"));
		return relationDao.showFollows(filter);
	}

	@Override
	public boolean createGroup(User user, String groupName) {
		StringBuffer sb = new StringBuffer();
		int count = random.nextInt(5);
		String groupNumber = "";
		for (int i = 0; i < 5 + count; i++) {
			int number = random.nextInt(10);
			sb.append(number);
		}
		groupNumber = sb.toString();
		return relationDao.addUserIntoGroup(groupName, groupNumber, user.getUsername(), user.getUsername());
		//return relationDao.createGroup(groupName, groupNumber, user);
	}

	@Override
	public List<Group> listGroups(User user) {
		Filter filter = new SingleColumnValueFilter(Bytes.toBytes(Constants.GROUP_MEMBER_FAMILY),
				Bytes.toBytes(Constants.GROUP_MEMBER_COLUMN[2]), CompareOperator.EQUAL,
				Bytes.toBytes(user.getUsername()));
		return relationDao.listGroups(filter);
	}
	
	@Override
	public List<Map<String, String>> showGroups(User user) {
		Filter filter = new SingleColumnValueFilter(Bytes.toBytes(Constants.GROUP_MEMBER_FAMILY),
				Bytes.toBytes(Constants.GROUP_MEMBER_COLUMN[2]), CompareOperator.EQUAL,
				Bytes.toBytes(user.getUsername()));
		return relationDao.showGroups(filter);
	}
	
	@Override
	public boolean addGroupMemeber(String groupName, String groupNumber, String groupOwner, String username) {
		return relationDao.addUserIntoGroup(groupName, groupNumber, groupOwner, username);
	}

	@Override
	public boolean dissmissGroup(String groupNumber) {
		Filter filter = new SingleColumnValueFilter(Bytes.toBytes(Constants.GROUP_MEMBER_FAMILY),
				Bytes.toBytes(Constants.GROUP_MEMBER_COLUMN[1]), CompareOperator.EQUAL, Bytes.toBytes(groupNumber));
		return relationDao.dismissGroup(filter);
	}

	@Override
	public boolean quitGroup(User user, String groupNumber) {
		String rowKey = user.getUsername() + "_" + groupNumber;
		return relationDao.quitGroup(rowKey);
	}

	@Override
	public boolean renameGroup(String groupNumber, String destName) {
		Filter filter = new SingleColumnValueFilter(Bytes.toBytes(Constants.GROUP_MEMBER_FAMILY),
				Bytes.toBytes(Constants.GROUP_MEMBER_COLUMN[1]), CompareOperator.EQUAL, Bytes.toBytes(groupNumber));
		return relationDao.renameGroup(filter, destName);
	}

	@Override
	public List<User> listGroupMember(String groupNumber) {
		Filter filter = new SingleColumnValueFilter(Bytes.toBytes(Constants.GROUP_MEMBER_FAMILY),
				Bytes.toBytes(Constants.GROUP_MEMBER_COLUMN[1]), CompareOperator.EQUAL, Bytes.toBytes(groupNumber));
		return relationDao.listGroupMember(filter);
	}

	@Override
	public Map<String, String> getGroupInfo(String groupNumber, String username) {
		String rowKey = username + "_" + groupNumber;
		return relationDao.getGroupInfo(rowKey);
	}

}
