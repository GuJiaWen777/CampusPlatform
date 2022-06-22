package com.ocean.platformspringboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocean.platformspringboot.common.Result;
import com.ocean.platformspringboot.service.IUserRoleService;
import com.ocean.platformspringboot.entity.UserRole;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户角色表 前端控制器
 * </p>
 *
 * @author gjw
 * @since 2022-03-25
 */
@RestController
@RequestMapping("/user-role")
public class UserRoleController {
    @Resource
    private IUserRoleService userRoleService;

    @PostMapping
    public Result save(@RequestBody UserRole userRole) {
            return Result.success(userRoleService.saveOrUpdate(userRole));
    }

    @GetMapping
    public Result findAll() {
            return Result.success(userRoleService.list());
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper <UserRole> queryWrapper=new QueryWrapper<>();
        return Result.success(userRoleService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

}
