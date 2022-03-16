package com.ocean.platformspringboot.mapper;

import com.ocean.platformspringboot.entity.dto.UserDTO;
import com.ocean.platformspringboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface  UserMapper {
    List<User> queryUserList();//查询用户列表
    User queryUserByID(@Param("id")Integer id);//根据用户ID查询用户
    User queryUserByName(@Param("name")String name);//根据用户名查询用户
    Integer addUser(User user);//增加一个用户
    Integer updateUser(User user);//更新用户数据
    Integer delUserByID(@Param("id")Integer id);//根据用户ID删除用户(假删除)
    List<User> selectPage(@Param("pageNum")Integer pageNum,@Param("pageSize")Integer pageSize);//分页查询所有用户数据
    Integer selectTotal();//获得未删除用户总数
    List<User> selectPageByName(@Param("pageNum")Integer pageNum,@Param("pageSize")Integer pageSize,@Param("name")String name);//分页查询根据用户名搜索的结果
    Integer selectTotalByName(@Param("name")String name);//获得根据用户名搜索的总数
    UserDTO loginByName(@Param("name")String name,@Param("pwd")String pwd);//查询用户名和密码对应
    Integer updateUserPoint(@Param("id")Integer id,@Param("point") Integer point);
}