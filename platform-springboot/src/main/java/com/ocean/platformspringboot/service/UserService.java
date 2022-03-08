package com.ocean.platformspringboot.service;

import com.ocean.platformspringboot.dao.UserServiceDao;
import com.ocean.platformspringboot.mapper.UserMapper;
import com.ocean.platformspringboot.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserServiceDao {
    @Resource
    UserMapper userMapper;
    List<User> userList;

    @Override
    public List<User> getAll(){
        userList = userMapper.queryUserList();
        return userList;
    }

    @Override
    public User getUserById(Integer id) { return userMapper.queryUserByID(id); }

    @Override
    public Integer saveUser(User user){//有id更新无id新增
        if(user.getId()==null){
            return userMapper.addUser(user);
        }else{
            return userMapper.updateUser(user);
        }

    }

    @Override
    public Integer delUserById(Integer id){
        return userMapper.delUserByID(id);
    }

    public Map<String,Object> findUserByPage(Integer pageNum,Integer pageSize){
        Map<String,Object> res = new HashMap<>();
        pageNum = (pageNum - 1) * pageSize;
        List<User> data = userMapper.selectPage(pageNum,pageSize);
        Integer total = userMapper.selectTotal();
        System.out.println("total:"+total);
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    public Map<String,Object> findUserByPageByName(Integer pageNum,Integer pageSize,String name){
        Map<String,Object> res = new HashMap<>();
        pageNum = (pageNum - 1) * pageSize;
        List<User> data = userMapper.selectPageByName(pageNum,pageSize,name);
        Integer total = userMapper.selectTotalByName(name);
        System.out.println("totalByName:"+total);
        res.put("data",data);
        res.put("total",total);
        return res;
    }

}
