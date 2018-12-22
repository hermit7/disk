package com.ise.dao;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.filter.Filter;

import com.ise.pojo.Group;
import com.ise.pojo.User;

public interface RelationDao {
	List<User> listFriends(User user, Filter filter);

	boolean remarkFriend(String userId, String friendId, String remark);

	boolean deleteFriend(String userId, String friendId);

	boolean follow(String userId, String friendId, String friendName);

	boolean createGroup(String groupName, String groupNumber, User user);

	boolean addUserIntoGroup(String groupName, String groupNumber, String groupOwner, String userId, String username);

	List<Group> listGroups(Filter filter);

	boolean dismissGroup(Filter filter);

	boolean quitGroup(String rowKey);

	boolean renameGroup(Filter filter, String destName);

	List<User> listGroupMember(Filter filter);

	Map<String, String> getGroupInfo(String rowKey);

}
