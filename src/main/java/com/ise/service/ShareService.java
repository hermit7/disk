package com.ise.service;

import java.util.List;

import com.ise.pojo.Share;
import com.ise.pojo.User;

public interface ShareService {
	boolean addShare(User user, String path, String receiveId, String receiveName);

	List<Share> listProvidedShare(User user);

	boolean deleteShare(String userId, String shareId);

	List<Share> listReceivedShare(User user);

	boolean saveShare(String path, String dst);

	boolean addGroupShare(User user, String path, String groupNumber, String groupName);

	List<Share> listGroupShare(String groupNumber);

	boolean deleteGroupShare(String groupNumber, String shareId);
}
