package com.ise.service;

import java.util.List;
import java.util.Map;

import com.ise.pojo.Group;
import com.ise.pojo.User;

public interface RelationService {

	List<User> listFriends(User user);

	boolean remarkFriend(String userId, String friendId, String remark);

	boolean deleteFriend(String userId, String friendId);

	boolean follow(String userId, String userId2, String username);

	List<Map<String, String>> showFollows(User user);

	boolean createGroup(User user, String groupName);

	List<Group> listGroups(User user);

	boolean addGroupMemeber(String groupName, String groupNumber,String groupOwner ,String username);

	boolean dissmissGroup(String groupNumber);

	boolean quitGroup(User user, String groupNumber);

	boolean renameGroup(String groupNumber, String destName);

	List<User> listGroupMember(String groupNumber);

	Map<String, String> getGroupInfo(String groupNumber, String username);

	List<Map<String, String>> showGroups(User user);

}
