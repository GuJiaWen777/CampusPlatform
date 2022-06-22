package com.ocean.platformspringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocean.platformspringboot.entity.Achievement;
import com.ocean.platformspringboot.entity.AchvRecord;
import com.ocean.platformspringboot.entity.TaskDone;
import com.ocean.platformspringboot.mapper.AchievementMapper;
import com.ocean.platformspringboot.mapper.AchvRecordMapper;
import com.ocean.platformspringboot.mapper.TaskDoneMapper;
import com.ocean.platformspringboot.service.IAchievementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 成就表 服务实现类
 * </p>
 *
 * @author gjw
 * @since 2022-03-27
 */
@Service
public class AchievementServiceImpl extends ServiceImpl<AchievementMapper, Achievement> implements IAchievementService {
    @Resource
    AchievementMapper achievementMapper;
    @Resource
    AchvRecordMapper recordMapper;
    @Resource
    TaskDoneMapper taskDoneMapper;

    @Override
    public Map<String, Object> getByUserId(Integer pageNum, Integer pageSize, Integer userId) {
        List<Integer> achvIds = recordMapper.selectAchieveIds(userId);
        QueryWrapper<Achievement> queryWrapper =new QueryWrapper<>();
        List<Achievement> achievementList= achievementMapper.selectList(queryWrapper);
        for(Achievement a:achievementList){
           if(achvIds.contains(a.getAchvId())){
               a.setOwned(true);
           }
        }
        Integer total = achievementList.size();
        List<Achievement> queryList = new ArrayList<>();
        int j = 0 ;
        int start = (pageNum -1) * pageSize ;
        int end = pageNum * pageSize - 1;
        for(int i = start ;i <= end;i++){
            if(i < total){
                queryList.add(j,achievementList.get(i));
                j++;
            }else {
                break;
            }
        }
        Map<String,Object> res = new HashMap<>();
        res.put("records",queryList);
        res.put("total",total);
        return res;
    }

    @Override
    public Boolean finishAchievement(Integer achvId, Integer userId) {
        Boolean finished = false;
        switch(achvId){
            case 1:
                finished = countTaskDone(achvId,userId,5);
                break;
            case 2:
                finished = countTaskDone(achvId,userId,10);
                break;
            case 3:
                finished = countNoPointTaskDone(achvId,userId,5);
                break;
            case 4:
                finished = countNoPointTaskDone(achvId,userId,10);
                break;
            default:
                break;
        }
        return finished;
    }

    private Boolean countTaskDone(Integer achvId,Integer userId,Integer need){
        QueryWrapper<TaskDone> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        Integer count = Math.toIntExact(taskDoneMapper.selectCount(queryWrapper));
        if(count >= need){
            AchvRecord record = new AchvRecord(userId,achvId, LocalDateTime.now());
            recordMapper.insert(record);
            return true;
        }else {
            return false;
        }
    }
    private Boolean countNoPointTaskDone(Integer achvId,Integer userId,Integer need){
        Integer count = taskDoneMapper.selectNoPointCount(userId);
        if(count >= need){
            AchvRecord record = new AchvRecord(userId,achvId, LocalDateTime.now());
            recordMapper.insert(record);
            return true;
        }else {
            return false;
        }
    }
}
