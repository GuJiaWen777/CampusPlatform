package com.ocean.platformspringboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ocean.platformspringboot.common.Constants;
import com.ocean.platformspringboot.entity.dto.UserDTO;
import com.ocean.platformspringboot.exception.ServiceException;
import com.ocean.platformspringboot.service.IUserService;
import com.ocean.platformspringboot.mapper.UserMapper;
import com.ocean.platformspringboot.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {
    @Resource
    UserMapper userMapper;

    @Override
    public List<User> getAll(){
        List<User> userList = userMapper.queryUserList();
        return userList;
    }

    @Override
    public User getUserById(Integer id) { return userMapper.queryUserByID(id); }

    @Override
    public User getUserByName(String name) {
        return userMapper.queryUserByName(name);
    }

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

    @Override
    public UserDTO login(UserDTO userDTO) {
        String name = userDTO.getName();
        String pwd = userDTO.getPwd();
        UserDTO loginUser = userMapper.loginByName(name,pwd);
        if( loginUser!= null ){
            return loginUser;
        }
        else{
            throw new ServiceException(Constants.CODE_606,"用户名或密码错误");
        }
    }

    @Override
    public User register(UserDTO userDTO) {
        String name = userDTO.getName();
        if(userMapper.selectTotalByName(name) == 0){
            User registerUser = new User();
            BeanUtil.copyProperties(userDTO,registerUser,true);
            userMapper.addUser(registerUser);
            return registerUser;
        }else{
            throw new ServiceException(Constants.CODE_606,"用户名已经被注册");
        }
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

    public Map<String,Object> findUserByPageByName(Integer pageNum, Integer pageSize, String name){
        Map<String,Object> res = new HashMap<>();
        pageNum = (pageNum - 1) * pageSize;
        List<User> userData = userMapper.selectPageByName(pageNum,pageSize,name);
        Integer total = userMapper.selectTotalByName(name);
        System.out.println("totalByName:"+total);
        res.put("userData",userData);
        res.put("total",total);
        return res;
    }

}
