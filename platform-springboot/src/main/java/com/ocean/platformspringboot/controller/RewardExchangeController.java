package com.ocean.platformspringboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocean.platformspringboot.common.Result;
import com.ocean.platformspringboot.service.IRewardExchangeService;
import com.ocean.platformspringboot.entity.RewardExchange;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 奖励品兑换表 前端控制器
 * </p>
 *
 * @author gjw
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/reward-exchange")
public class RewardExchangeController {
    @Resource
    private IRewardExchangeService rewardExchangeService;

    @PostMapping
    public Result exchange(@RequestBody RewardExchange rewardExchange) {
        if(rewardExchangeService.exchangeReward(rewardExchange)){
            return Result.success();
        }else{
            return Result.error();
        }

    }
    @GetMapping("/page")
    public Result findMyRecord(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize){
        return Result.success(rewardExchangeService.findRecord(new Page<>(pageNum, pageSize)));
    }
    @GetMapping("/page/{id}")
    public Result findMyRecord(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @PathVariable("id") Integer userId){
        Page<RewardExchange> page = rewardExchangeService.findRecordByUser(new Page<>(pageNum, pageSize),userId);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public  Result findOne(@PathVariable("id") Integer id) {
            return Result.success(rewardExchangeService.getById(id));
    }

    @PutMapping("/deliver/{rewardId}")
    public Result deliverReward(@PathVariable("rewardId") Integer id){
        return Result.success(rewardExchangeService.deliverReward(id));
    }


}
