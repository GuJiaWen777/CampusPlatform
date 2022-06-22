package com.ocean.platformspringboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.platformspringboot.entity.RewardExchange;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 奖励品兑换表 服务类
 * </p>
 *
 * @author gjw
 * @since 2022-03-18
 */
public interface IRewardExchangeService extends IService<RewardExchange> {

    Boolean exchangeReward(RewardExchange rewardExchange);

    Page<RewardExchange> findRecordByUser(Page<RewardExchange> page, Integer userId);

    Page<RewardExchange> findRecord(Page<RewardExchange> page);

    Integer deliverReward(Integer id);

}
