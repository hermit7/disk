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

	boolean createGroup(User user, String groupName);

	List<Group> listGroups(User user);

	boolean addGroupMemeber(String groupName, String groupNumber, String groupOwner, String userId, String username);

	boolean dissmissGroup(User user, String groupNumber);

	boolean quitGroup(User user, String groupNumber);

	boolean renameGroup(User user, String groupNumber, String destName);

	List<User> listGroupMember(String groupNumber);

	Map<String, String> getGroupInfo(String groupNumber, String userId);

}
