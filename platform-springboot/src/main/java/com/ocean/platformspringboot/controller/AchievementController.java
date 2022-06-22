package com.ocean.platformspringboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.platformspringboot.service.IUserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocean.platformspringboot.common.Result;
import com.ocean.platformspringboot.service.IAchievementService;
import com.ocean.platformspringboot.entity.Achievement;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 成就表 前端控制器
 * </p>
 *
 * @author gjw
 * @since 2022-03-27
 */
@RestController
@RequestMapping("/achievement")
public class AchievementController {
    @Resource
    private IAchievementService achievementService;
    @Resource
    private IUserService userService;

    @PostMapping
    public Result save(@RequestBody Achievement achievement) {
            return Result.success(achievementService.saveOrUpdate(achievement));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
            return Result.success(achievementService.removeById(id));
    }

    @GetMapping
    public Result findAll() {
            return Result.success(achievementService.list());
    }

    @GetMapping("/{id}")
    public  Result findOne(@PathVariable Integer id) {
            return Result.success(achievementService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper <Achievement> queryWrapper=new QueryWrapper<>();
        return Result.success(achievementService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    @GetMapping("/page/{id}")
    public Result findPageById(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @PathVariable("id") Integer userId){
        Map<String,Object> res = achievementService.getByUserId(pageNum,pageSize,userId);
        return Result.success(res);
    }

    @GetMapping("/finish/{id}/{userId}")
    public Result finishAchievement(@PathVariable("id") Integer achvId,
                                    @PathVariable("userId") Integer userId){
        Boolean res = achievementService.finishAchievement(achvId,userId);
        if(res){
            userService.addLevelForUser(userId);//完成成就增加经验
            return Result.success();
        }
        return Result.error();

    }

}
