package com.ocean.platformspringboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.platformspringboot.entity.RewardExchange;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * <p>
 * 奖励品兑换表 Mapper 接口
 * </p>
 *
 * @author gjw
 * @since 2022-03-18
 */
public interface RewardExchangeMapper extends BaseMapper<RewardExchange> {

    Page<RewardExchange> findRecordByUser(Page<RewardExchange> page, @Param("userId") Integer userId);

    Page<RewardExchange> findRecord(Page<RewardExchange> page);

    Integer updateStatus(@Param("id") Integer id);

    Integer save(@Param("userID") Integer userID, @Param("rewardId") Integer rewardId, @Param("exchangeNum") Integer exchangeNum, @Param("time") LocalDateTime time);

}
