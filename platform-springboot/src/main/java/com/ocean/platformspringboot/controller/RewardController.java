package com.ocean.platformspringboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocean.platformspringboot.common.Result;
import com.ocean.platformspringboot.service.IRewardService;
import com.ocean.platformspringboot.entity.Reward;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 奖励品表 前端控制器
 * </p>
 *
 * @author gjw
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/reward")
public class RewardController {
    @Resource
    private IRewardService rewardService;

    @PostMapping
    public Result save(@RequestBody Reward reward) {
            return Result.success(rewardService.saveOrUpdate(reward));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
            return Result.success(rewardService.removeById(id));
    }

    @GetMapping
    public Result findAll() {
            return Result.success(rewardService.list());
    }

    @GetMapping("/{id}")
    public  Result findOne(@PathVariable Integer id) {
            return Result.success(rewardService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String rewardName) {
        QueryWrapper <Reward> queryWrapper=new QueryWrapper<>();
        if(!"".equals(rewardName)){
            queryWrapper.like("reward_name",rewardName);
        }
        return Result.success(rewardService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }
    @GetMapping("/page/common")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper <Reward> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("reward_over",1);
        return Result.success(rewardService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }
    @PutMapping("/publish/{id}") //通过审核，上架奖励品
    public Result publishTask(@PathVariable("id") Integer id){
        Reward one = rewardService.getById(id);
        one.setRewardOver(1);
        return Result.success(rewardService.updateById(one));
    }
    @PutMapping("/return/{id}") //通过审核，下架奖励品
    public Result returnTask(@PathVariable("id") Integer id){
        Reward one = rewardService.getById(id);
        one.setRewardOver(0);
        return Result.success(rewardService.updateById(one));
    }

}
