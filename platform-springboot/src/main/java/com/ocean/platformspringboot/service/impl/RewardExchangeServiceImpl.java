package com.ocean.platformspringboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.platformspringboot.entity.PointRecord;
import com.ocean.platformspringboot.entity.Reward;
import com.ocean.platformspringboot.entity.RewardExchange;
import com.ocean.platformspringboot.entity.User;
import com.ocean.platformspringboot.mapper.PointRecordMapper;
import com.ocean.platformspringboot.mapper.RewardExchangeMapper;
import com.ocean.platformspringboot.mapper.RewardMapper;
import com.ocean.platformspringboot.mapper.UserMapper;
import com.ocean.platformspringboot.service.IRewardExchangeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 奖励品兑换表 服务实现类
 * </p>
 *
 * @author gjw
 * @since 2022-03-18
 */
@Service
public class RewardExchangeServiceImpl extends ServiceImpl<RewardExchangeMapper, RewardExchange> implements IRewardExchangeService {

    @Resource
    RewardMapper rewardMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    PointRecordMapper recordMapper;
    @Resource
    RewardExchangeMapper exchangeMapper;

    @Override
    @Transactional
    public Boolean exchangeReward(RewardExchange rewardExchange) {
        Integer userID = rewardExchange.getUserId();
        Integer rewardId = rewardExchange.getRewardId();
        Integer exchangeNum = rewardExchange.getRewardNum();
        Reward reward = rewardMapper.selectById(rewardId);
        Integer point = reward.getRewardPoint();
        Integer num = reward.getRewardNum();
        if(num - exchangeNum > 0){
            User exchanger= userMapper.queryUserByID(userID);
            reward.setRewardNum( num - exchangeNum);
            int res = exchanger.getPoint() - point * exchangeNum;
            if(res >= 0){
                PointRecord record = new PointRecord();
                record.setUserId(userID);
                record.setPointNum(point * exchangeNum);
                record.setAlterDescription("兑换奖励扣除积分");
                record.setRecordTime(LocalDateTime.now());
                rewardMapper.updateById(reward);
                recordMapper.insert(record);
                userMapper.updateUserPoint(userID ,res);
                exchangeMapper.save(userID,rewardId,exchangeNum,LocalDateTime.now());
                return true;
            }
        }else{
            reward.setRewardOver(0);
            return false;
        }
        return false;
    }

    @Override
    public Page<RewardExchange> findRecordByUser(Page<RewardExchange> page, Integer userId) {
        return exchangeMapper.findRecordByUser(page,userId);

    }

    @Override
    public Page<RewardExchange> findRecord(Page<RewardExchange> page) {
        return exchangeMapper.findRecord(page);

    }

    @Override
    public Integer deliverReward(Integer id) {
        return exchangeMapper.updateStatus(id);
    }
}
