package com.ocean.platformspringboot.service.impl;

import com.ocean.platformspringboot.entity.Reward;
import com.ocean.platformspringboot.mapper.RewardMapper;
import com.ocean.platformspringboot.service.IRewardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 奖励品表 服务实现类
 * </p>
 *
 * @author gjw
 * @since 2022-03-18
 */
@Service
public class RewardServiceImpl extends ServiceImpl<RewardMapper, Reward> implements IRewardService {
    @Resource
    RewardMapper rewardMapper;
    @Override
    public Integer selectCount() {
        return rewardMapper.selectList(null).size();
    }
}
