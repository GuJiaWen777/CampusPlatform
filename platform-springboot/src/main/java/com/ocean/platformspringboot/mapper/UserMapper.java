package com.ocean.platformspringboot.mapper;

import com.ocean.platformspringboot.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper
public interface  UserMapper {
    List<User> queryUserList();
    User queryUserByID(Integer id);
    Integer addUser(User user);
    Integer updateUser(User user);
    Integer delUserByID(@Param("id")Integer id);
    List<User> selectPage(Integer pageNum,Integer pageSize);//分页查询所有
    Integer selectTotal();//获得总数
    List<User> selectPageByName(Integer pageNum,Integer pageSize,String name);//分页查询根据用户名搜索的结果
    Integer selectTotalByName(String name);//获得根据用户名搜索的总数
}