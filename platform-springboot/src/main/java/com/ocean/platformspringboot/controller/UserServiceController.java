package com.ocean.platformspringboot.controller;

import com.ocean.platformspringboot.pojo.User;
import com.ocean.platformspringboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserServiceController {
    @Resource
    UserService userService;

    //新增和修改
    @PostMapping
    public Integer saveUser(@RequestBody User user){
        System.out.println("saveUser:"+user);
        return userService.saveUser(user);
    }

    //查询所有数据
    @GetMapping
    public List<User> getUserAll(){
        List<User> users = userService.getAll();
        return users;
    }

    //删除指定数据
    @DeleteMapping("/{id}")
    public Integer deleteUser(@PathVariable Integer id){
        return userService.delUserById(id);
    }

    //接口路径/userList/page?pageNum=?&pageSize=?
    //查询分页数据
    //根据用户名模糊查询
    @GetMapping("/page")
    public Map<String,Object> getUserPageByName(@RequestParam Integer pageNum,
                                                @RequestParam Integer pageSize,
                                                @RequestParam String name){
        Map<String,Object> res = userService.findUserByPageByName(pageNum,pageSize,name);
        return res;
    }


}
