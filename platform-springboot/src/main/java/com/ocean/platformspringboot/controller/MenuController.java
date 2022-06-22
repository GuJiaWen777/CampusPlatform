package com.ocean.platformspringboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocean.platformspringboot.common.Result;
import com.ocean.platformspringboot.service.IMenuService;
import com.ocean.platformspringboot.entity.Menu;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author gjw
 * @since 2022-03-26
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private IMenuService menuService;

    @PostMapping
    public Result save(@RequestBody Menu menu) {
            return Result.success(menuService.saveOrUpdate(menu));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
            return Result.success(menuService.removeById(id));
    }

    @GetMapping
    public Result findAll() {
            return Result.success(menuService.list());
    }

    @GetMapping("/{id}")
    public  Result findOne(@PathVariable Integer id) {
            return Result.success(menuService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper <Menu> queryWrapper=new QueryWrapper<>();
        return Result.success(menuService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

}
