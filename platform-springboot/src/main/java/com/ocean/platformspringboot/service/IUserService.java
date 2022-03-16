package com.ocean.platformspringboot.service;

import com.ocean.platformspringboot.entity.dto.UserDTO;
import com.ocean.platformspringboot.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserService {

    List<User> getAll();//获得所有用户数据
    User getUserById(Integer id);//根据用户ID查询用户
    User getUserByName(String name);//根据用户ID查询用户
    Integer saveUser(User user);//保存用户数据
    Integer delUserById(Integer id);//根据用户ID删除用户
    UserDTO login(UserDTO userDTO);//用户登录
    User register(UserDTO userDTO);//用户注册



}
