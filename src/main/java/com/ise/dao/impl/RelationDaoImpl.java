package com.ise.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ise.constant.Constants;
import com.ise.dao.HBaseDao;
import com.ise.dao.RelationDao;
import com.ise.pojo.Group;
import com.ise.pojo.User;

@Repository("relationDao")
public class RelationDaoImpl implements RelationDao {

	@Autowired
	private HBaseDao hbaseDao;

	@Autowired
	private DataSource dataSource;

	/**
	 * 展示关注对象的所有信息
	 */
	@Override
	public List<User> listFriends(User user, Filter filter) {
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

	@Override
	public boolean createGroup(String groupName, String groupNumber, User user) {
		Connection conn = null;
		int i = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO `group`(`group_name`,`group_number`,`owner`,`owner_id`)" + " values(?,?,?,?)";
			try {
				conn = dataSource.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, groupName);
				pst.setString(2, groupNumber);
				pst.setString(3, user.getUsername());
				pst.setString(4, user.getUserId());
				i = pst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i > 0 ? true : false;
	}

	@Override
	public boolean addUserIntoGroup(String groupName, String groupNumber, String groupOwner, String userId,
			String username) {
		boolean existed = hbaseDao.recordExisted(Constants.GROUP_MEMBER_TABLE, userId + "_" + groupNumber);
		if (!existed) {
			hbaseDao.updateOneData(Constants.GROUP_MEMBER_TABLE, userId + "_" + groupNumber,
					Constants.GROUP_MEMBER_FAMILY, Constants.GROUP_MEMBER_COLUMN[0], groupName);
			hbaseDao.updateOneData(Constants.GROUP_MEMBER_TABLE, userId + "_" + groupNumber,
					Constants.GROUP_MEMBER_FAMILY, Constants.GROUP_MEMBER_COLUMN[1], groupNumber);
			hbaseDao.updateOneData(Constants.GROUP_MEMBER_TABLE, userId + "_" + groupNumber,
					Constants.GROUP_MEMBER_FAMILY, Constants.GROUP_MEMBER_COLUMN[2], userId);
			hbaseDao.updateOneData(Constants.GROUP_MEMBER_TABLE, userId + "_" + groupNumber,
					Constants.GROUP_MEMBER_FAMILY, Constants.GROUP_MEMBER_COLUMN[3], username);
			hbaseDao.updateOneData(Constants.GROUP_MEMBER_TABLE, userId + "_" + groupNumber,
					Constants.GROUP_MEMBER_FAMILY, Constants.GROUP_MEMBER_COLUMN[4], groupOwner);
		}
		return true;
	}

	@Override
	public List<Group> listGroups(Filter filter) {
		ResultScanner rs = hbaseDao.getResultScannerByFilter(Constants.GROUP_MEMBER_TABLE, filter);
		List<Group> list = new ArrayList<>();
		Group group = null;
		Iterator<Result> iter = rs.iterator();
		while (iter.hasNext()) {
			group = new Group();
			Result result = iter.next();
			String groupname = Bytes.toString(result.getValue(Bytes.toBytes(Constants.GROUP_MEMBER_FAMILY),
					Bytes.toBytes(Constants.GROUP_MEMBER_COLUMN[0])));
			String groupnumber = Bytes.toString(result.getValue(Bytes.toBytes(Constants.GROUP_MEMBER_FAMILY),
					Bytes.toBytes(Constants.GROUP_MEMBER_COLUMN[1])));
			String owner = Bytes.toString(result.getValue(Bytes.toBytes(Constants.GROUP_MEMBER_FAMILY),
					Bytes.toBytes(Constants.GROUP_MEMBER_COLUMN[4])));
			group.setGroupName(groupname);
			group.setGroupNumber(groupnumber);
			group.setOwner(owner);
			list.add(group);
		}
		return list;
	}

	@Override
	public boolean dismissGroup(Filter filter) {
		boolean flag = false;
		ResultScanner rs = hbaseDao.getResultScannerByFilter(Constants.GROUP_MEMBER_TABLE, filter);
		for (Result result : rs) {
			byte[] row = result.getRow();
			Delete delete = new Delete(row);
			flag = hbaseDao.deleteData(Constants.GROUP_MEMBER_TABLE, delete);
		}
		return flag;
	}

	@Override
	public boolean quitGroup(String rowKey) {
		return hbaseDao.deleteDataByRow(Constants.GROUP_MEMBER_TABLE, rowKey);
	}

	@Override
	public boolean renameGroup(Filter filter, String destName) {
		boolean flag = false;
		ResultScanner rs = hbaseDao.getResultScannerByFilter(Constants.GROUP_MEMBER_TABLE, filter);
		for (Result result : rs) {
			byte[] row = result.getRow();
			flag = hbaseDao.updateOneData(Constants.GROUP_MEMBER_TABLE, Bytes.toString(row),
					Constants.GROUP_MEMBER_FAMILY, Constants.GROUP_MEMBER_COLUMN[0], destName);
		}
		return flag;
	}

	@Override
	public List<User> listGroupMember(Filter filter) {
		ResultScanner rs = hbaseDao.getResultScannerByFilter(Constants.GROUP_MEMBER_TABLE, filter);
		List<User> list = new ArrayList<>();
		User member = null;
		Iterator<Result> iter = rs.iterator();
		while (iter.hasNext()) {
			member = new User();
			Result result = iter.next();
			String userId = Bytes.toString(result.getValue(Bytes.toBytes(Constants.GROUP_MEMBER_FAMILY),
					Bytes.toBytes(Constants.GROUP_MEMBER_COLUMN[2])));
			String username = Bytes.toString(result.getValue(Bytes.toBytes(Constants.GROUP_MEMBER_FAMILY),
					Bytes.toBytes(Constants.GROUP_MEMBER_COLUMN[3])));
			member.setUserId(userId);
			member.setUsername(username);
			list.add(member);
		}
		return list;
	}

	@Override
	public Map<String, String> getGroupInfo(String rowKey) {
		HashMap<String, String> map = new HashMap<>();
		Result result = hbaseDao.getResultByRow(Constants.GROUP_MEMBER_TABLE, rowKey);
		String groupName = Bytes.toString(result.getValue(Bytes.toBytes(Constants.GROUP_MEMBER_FAMILY),
				Bytes.toBytes(Constants.GROUP_MEMBER_COLUMN[0])));
		String owner = Bytes.toString(result.getValue(Bytes.toBytes(Constants.GROUP_MEMBER_FAMILY),
				Bytes.toBytes(Constants.GROUP_MEMBER_COLUMN[4])));
		map.put("groupName", groupName);
		map.put("owner", owner);
		return map;
	}

}
