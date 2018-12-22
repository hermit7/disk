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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ise.constant.Constants;
import com.ise.dao.RelationDao;
import com.ise.pojo.Group;
import com.ise.pojo.User;
import com.ise.service.RelationService;

@Service("relationService")
public class RelationServiceImpl implements RelationService {

	private static final Random random = new Random();

	@Autowired
	private RelationDao relationDao;

	@Cacheable(value = "friends", key = "'friends_' + #user.userId")
	@Override
	public List<User> listFriends(User user) {
		Filter filter = new PrefixFilter(Bytes.toBytes(user.getUserId() + "_"));
		return relationDao.listFriends(user, filter);
	}

	@CacheEvict(value = "friends", key = "'friends_'+ #userId")
	@Override
	public boolean remarkFriend(String userId, String friendId, String remark) {
		return relationDao.remarkFriend(userId, friendId, remark);
	}

	@CacheEvict(value = "friends", key = "'friends_'+ #userId")
	@Override
	public boolean deleteFriend(String userId, String friendId) {
		return relationDao.deleteFriend(userId, friendId);
	}

	@CacheEvict(value = "friends", key = "'friends_'+ #userId")
	@Override
	public boolean follow(String userId, String friendId, String friendName) {
		return relationDao.follow(userId, friendId, friendName);
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
		return relationDao.addUserIntoGroup(groupName, groupNumber, user.getUsername(), user.getUserId(),
				user.getUsername());
		// return relationDao.createGroup(groupName, groupNumber, user);
	}

	@Override
	public List<Group> listGroups(User user) {
		/*
		 * Filter filter = new
		 * SingleColumnValueFilter(Bytes.toBytes(Constants.GROUP_MEMBER_FAMILY),
		 * Bytes.toBytes(Constants.GROUP_MEMBER_COLUMN[2]), CompareOperator.EQUAL,
		 * Bytes.toBytes(user.getUsername()));
		 */
		Filter filter = new PrefixFilter(Bytes.toBytes(user.getUserId() + "_"));
		return relationDao.listGroups(filter);
	}

	@CacheEvict(value = "groupMember", key = "'groupMember_' + #groupNumber")
	@Override
	public boolean addGroupMemeber(String groupName, String groupNumber, String groupOwner, String userId,
			String username) {
		return relationDao.addUserIntoGroup(groupName, groupNumber, groupOwner, userId, username);
	}
	
	@CacheEvict(value = "groupMember", key = "'groupMember_' + #groupNumber")
	@Override
	public boolean dissmissGroup(User user, String groupNumber) {
		Filter filter = new SingleColumnValueFilter(Bytes.toBytes(Constants.GROUP_MEMBER_FAMILY),
				Bytes.toBytes(Constants.GROUP_MEMBER_COLUMN[1]), CompareOperator.EQUAL, Bytes.toBytes(groupNumber));
		return relationDao.dismissGroup(filter);
	}

	@CacheEvict(value = "groupMember", key = "'groupMember_' + #groupNumber")
	@Override
	public boolean quitGroup(User user, String groupNumber) {
		String rowKey = user.getUserId() + "_" + groupNumber;
		return relationDao.quitGroup(rowKey);
	}

	@Override
	public boolean renameGroup(User user, String groupNumber, String destName) {
		Filter filter = new SingleColumnValueFilter(Bytes.toBytes(Constants.GROUP_MEMBER_FAMILY),
				Bytes.toBytes(Constants.GROUP_MEMBER_COLUMN[1]), CompareOperator.EQUAL, Bytes.toBytes(groupNumber));
		return relationDao.renameGroup(filter, destName);
	}

	@Cacheable(value = "groupMember", key = "'groupMember_' + #groupNumber")
	@Override
	public List<User> listGroupMember(String groupNumber) {
		Filter filter = new SingleColumnValueFilter(Bytes.toBytes(Constants.GROUP_MEMBER_FAMILY),
				Bytes.toBytes(Constants.GROUP_MEMBER_COLUMN[1]), CompareOperator.EQUAL, Bytes.toBytes(groupNumber));
		return relationDao.listGroupMember(filter);
	}

	@Override
	public Map<String, String> getGroupInfo(String groupNumber, String userId) {
		String rowKey = userId + "_" + groupNumber;
		return relationDao.getGroupInfo(rowKey);
	}

}
