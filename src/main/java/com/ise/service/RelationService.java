package com.ise.service;

import java.util.List;
import java.util.Map;

import com.ise.pojo.User;

public interface RelationService {

	List<User> listFriends(User user);

	boolean remarkFriend(String userId, String friendId, String remark);

	boolean deleteFriend(String userId, String friendId);

	boolean follow(String userId, String userId2, String username);

	List<Map<String, String>> showFollows(User user);

}
