package com.ise.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ise.dao.RelationDao;
import com.ise.pojo.User;
import com.ise.service.RelationService;

@Service("relationService")
public class RelationServiceImpl implements RelationService {

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
		return relationDao.showFollows(user);
	}

}
