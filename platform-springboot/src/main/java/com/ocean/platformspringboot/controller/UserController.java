package com.ocean.platformspringboot.controller;

import cn.hutool.core.util.StrUtil;
import com.ocean.platformspringboot.common.Constants;
import com.ocean.platformspringboot.common.Result;
import com.ocean.platformspringboot.entity.dto.UserDTO;
import com.ocean.platformspringboot.entity.User;
import com.ocean.platformspringboot.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserServiceImpl userServiceImpl;

    //新增和修改用户
    @PostMapping
    public Result saveUser(@RequestBody User user){
        System.out.println("saveUser:"+user);
        return Result.success(userServiceImpl.saveUser(user));
    }

    //登陆请求
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        String name = userDTO.getName();
        String pwd = userDTO.getPwd();
        if(StrUtil.isBlank(name) || StrUtil.isBlank(pwd) ){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO loginUser = userServiceImpl.login(userDTO);
        return Result.success(loginUser);
    }

    //注册请求
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO){
        String name = userDTO.getName();
        String pwd = userDTO.getPwd();
        String num = userDTO.getNum();
        if(StrUtil.isBlank(name) || StrUtil.isBlank(pwd) || StrUtil.isBlank(num)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        User registerUser = userServiceImpl.register(userDTO);
        return Result.success(registerUser);
    }

    //查询所有用户数据
    @GetMapping
    public Result getUserAll(){
        List<User> users = userServiceImpl.getAll();
        return Result.success(users);
    }

    //
    @GetMapping("/name/{name}")
    public Result getUserByName(@PathVariable String name){
        return Result.success(userServiceImpl.getUserByName(name));
    }


    //删除指定ID用户
    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable Integer id){
        return Result.success(userServiceImpl.delUserById(id));
    }

    //接口路径/userList/page?pageNum=?&pageSize=?
    //查询分页数据
    //根据用户名模糊查询
    @GetMapping("/page")
    public Result getUserPageByName(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam String username){
        Map<String,Object> res = userServiceImpl.findUserByPageByName(pageNum,pageSize,username);
        return Result.success(res);
    }


}
