package com.ise.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ise.pojo.User;

public interface UserMapper {

	User login(@Param(value = "username") String username, @Param(value = "password") String password);

	User findUser(String username);

	List<User> findAllUsers();

}
