package com.ocean.platformspringboot.service;

import com.ocean.platformspringboot.entity.Reward;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 奖励品表 服务类
 * </p>
 *
 * @author gjw
 * @since 2022-03-18
 */
public interface IRewardService extends IService<Reward> {

    Integer selectCount();

}
