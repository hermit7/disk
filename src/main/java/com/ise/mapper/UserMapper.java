package com.ise.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ise.pojo.User;

public interface UserMapper {

	User login(@Param(value = "username") String username, @Param(value = "password") String password);

	User findUser(String username);

	List<User> findAllUsers();

	void increaseUsedSpace(@Param(value = "userId") String userId, @Param(value = "size") Long size);
	
	void decreaseUsedSpace(@Param(value = "userId") String userId, @Param(value = "size") Long size);

	boolean banUser(String userId);

	boolean permitUser(String userId);

	boolean dilatation(String userId); //扩容

	boolean reduction(String userId);

}
