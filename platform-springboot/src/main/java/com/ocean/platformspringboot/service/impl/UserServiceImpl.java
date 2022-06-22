package com.ocean.platformspringboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocean.platformspringboot.common.Constants;
import com.ocean.platformspringboot.entity.Menu;
import com.ocean.platformspringboot.entity.UserRole;
import com.ocean.platformspringboot.entity.dto.UserDTO;
import com.ocean.platformspringboot.entity.dto.UserPasswordDTO;
import com.ocean.platformspringboot.exception.ServiceException;
import com.ocean.platformspringboot.mapper.*;
import com.ocean.platformspringboot.service.IUserService;
import com.ocean.platformspringboot.entity.User;
import com.ocean.platformspringboot.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    @Resource
    UserMapper userMapper;
    @Resource
    UserRoleMapper userRoleMapper;
    @Resource
    MenuMapper menuMapper;
    @Resource
    TaskDoneMapper taskDoneMapper;
    @Resource
    TaskReceivedMapper taskReceivedMapper;

    @Override
    public List<User> getAll(){
        return userMapper.queryUserList();
    }

    @Override
    public User getUserById(Integer id) { return userMapper.queryUserByID(id); }

    @Override
    public User getUserByName(String name) {
        return userMapper.queryUserByName(name);
    }

    @Override
    public Integer saveUser(User user){//有id更新无id新增
        String name = user.getName();
        Integer userId = user.getId();
        if(userId == null){
            user.setPwd("123456");
            Integer res = userMapper.addUser(user);
            //新增之后为新角色分配角色
            addRoleByName(name);
            return res;
        }else{
            if(userRoleMapper.selectUserCount(userId) < 1){
                addRoleByName(name);
            }else {
                Integer roleId = user.getRoleId() == null ?  userRoleMapper.selectRoleByUser(userId): user.getRoleId();
                userRoleMapper.updateByUser(userId,roleId);
            }
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
            String token = TokenUtils.genToken(loginUser.getId().toString(),loginUser.getPwd());
            loginUser.setToken(token);
            Integer userId = loginUser.getId();
            Integer roleId;
            roleId= userRoleMapper.selectRoleByUser(userId);
            loginUser.setRoleId(roleId);
            List<Menu> roleMenus = getRoleMenus(roleId);
            loginUser.setMenus(roleMenus);
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
            //新增之后为新角色分配角色
            addRoleByName(name);
            return registerUser;
        }else{
            throw new ServiceException(Constants.CODE_606,"用户名已经被注册");
        }
    }

    public Map<String,Object> findUserByPageByName(Integer pageNum, Integer pageSize, String name){
        Map<String,Object> res = new HashMap<>();
        pageNum = (pageNum - 1) * pageSize;
        List<User> userData = userMapper.selectPageByName(pageNum,pageSize,name);
        Integer total = userMapper.selectTotalByName(name);
        res.put("userData",userData);
        res.put("total",total);
        return res;
    }

    //根据角色ID分配菜单
    private List<Menu> getRoleMenus(Integer roleId){
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort_num");
        List<Menu> list = menuMapper.selectList(queryWrapper);
        List<Menu> menus = new ArrayList<>();
        //找出pid为null的一级菜单
        List<Menu> parentNodes = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        for (Menu menu : parentNodes) {
            // 筛选所有数据中pid=父级id的数据就是二级菜单
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        if(roleId == 1){
            menus.add(parentNodes.get(0));
            menus.add(parentNodes.get(1));
            menus.add(parentNodes.get(2));
            menus.add(parentNodes.get(3));
        }else {
            menus.add(parentNodes.get(0));
            menus.addAll(parentNodes.subList(2, 6));
        }
        return menus;
    }

    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        int update = userMapper.updatePassword(userPasswordDTO);
        if (update < 1) {
            throw new ServiceException(Constants.CODE_606, "原密码错误");
        }
    }

    @Override
    public Integer selectCount() {
        return userMapper.selectTotal();
    }

    @Override
    public void deductCredit(Integer userId,Integer taskPoints) {
        User one = userMapper.queryUserByID(userId);
        int newCredit = one.getCredit() - CalculateCoefficient(one , taskPoints );
        if(newCredit >= 60){
            userMapper.updateUserCredit(newCredit,userId);
        }else if(one.getBlock_status() == 0) {
            userMapper.updateUserCredit(59,userId);
            userMapper.updateUserBlock(userId,1);
        }
    }

    @Override
    public void addCredit(Integer userId) {
        User one = userMapper.queryUserByID(userId);
        int newCredit = one.getCredit() + 1;
        if(newCredit <= 100){
            userMapper.updateUserCredit(newCredit,userId);
        } else{
            userMapper.updateUserCredit(100,userId);
        }
    }

    @Override
    public Integer getUserStatusById(Integer userId) {
        return userMapper.selectUserStatus(userId);
    }

    @Override
    public Integer addLevelForUser(Integer userId) {
        User user = userMapper.queryUserByID(userId);
        if(user.getLevel() <= 600){
            return userMapper.updateUserLevel(userId,user.getLevel() + 10);
        }else {
            return 0;
        }

    }


    private void addRoleByName(String name){
        User userForRole = userMapper.queryUserByName(name);
        userForRole.setRoleId(2);
        UserRole userRole = new UserRole(userForRole.getId(),2);
        userRoleMapper.insert(userRole);
    }

    private int CalculateCoefficient(User one,Integer taskPoints){
        int point = Math.min(taskPoints / 10, 5);//积分多扣得多
        int level = Math.min(one.getLevel() / 100 , 6);//等级高扣得少
        int userId = one.getId();
        int doneNum =  taskDoneMapper.selectCountByUser(userId) ;
        int receivedNum = taskReceivedMapper.selectCountByUser(userId) ;
        int task = Math.min(receivedNum - doneNum, 5);//差值越大扣的越多
        return  (int) (2 - level * 0.3 + point * 0.5 + task * 0.2 );
    }

}
