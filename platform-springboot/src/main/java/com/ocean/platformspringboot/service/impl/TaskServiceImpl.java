package com.ocean.platformspringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.platformspringboot.entity.PointRecord;
import com.ocean.platformspringboot.entity.Task;
import com.ocean.platformspringboot.entity.User;
import com.ocean.platformspringboot.mapper.PointRecordMapper;
import com.ocean.platformspringboot.mapper.TaskMapper;
import com.ocean.platformspringboot.mapper.UserMapper;
import com.ocean.platformspringboot.service.ITaskService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 任务表 服务实现类
 * </p>
 *
 * @author gjw
 * @since 2022-03-09
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

    @Resource
    TaskMapper taskMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    PointRecordMapper recordMapper;

    @Override
    public Page<Task> findpage(Page<Task> page, String taskTitle) {
        return taskMapper.findpage(page,taskTitle);
    }

    @Override
    public Page<Task> findpageCommon(Page<Task> page, String taskTitle) {
        return taskMapper.findpageCommon(page,taskTitle);
    }

    @Override
    public Page<Task> findReceivedTaskById(Page<Task> page, String taskTitle, Integer userId) {
        return taskMapper.findReceivedTaskById(page,taskTitle,userId);
    }

    @Override
    public Page<Task> findReleasedTaskById(Page<Task> page, String taskTitle, Integer userId) {
        return taskMapper.findReleasedTaskById(page,taskTitle,userId);
    }


    @Override
    public Integer addPointForUser(Integer userId, Integer taskId) {
        User user = userMapper.queryUserByID(userId);
        Task task = taskMapper.selectById(taskId);
        Integer taskPoint = task.getTaskPoints();
        PointRecord record = new PointRecord();
        record.setUserId(userId);
        record.setPointNum(taskPoint);
        record.setAlterDescription("完成任务获得积分");
        record.setRecordTime(LocalDateTime.now());
        recordMapper.insert(record);
        return userMapper.updateUserPoint(userId ,user.getPoint() + taskPoint);
    }

    @Override
    public Integer returnPointForUser(Integer userId, Integer taskId) {
        User user = userMapper.queryUserByID(userId);
        Task task = taskMapper.selectById(taskId);
        Integer taskPoint = task.getTaskPoints();
        PointRecord record = new PointRecord();
        record.setUserId(userId);
        record.setPointNum(taskPoint);
        record.setAlterDescription("退回任务返回积分");
        record.setRecordTime(LocalDateTime.now());
        recordMapper.insert(record);
        return userMapper.updateUserPoint(userId ,user.getPoint() + taskPoint);
    }

    @Override
    public Boolean deductPointForUser(Integer userId, Integer taskPoint) {
        User user = userMapper.queryUserByID(userId);
        Integer res = user.getPoint() - taskPoint;
        if(res >= 0){
            PointRecord record = new PointRecord();
            record.setUserId(userId);
            record.setPointNum(taskPoint);
            record.setAlterDescription("发布任务扣除积分");
            record.setRecordTime(LocalDateTime.now());
            recordMapper.insert(record);
            userMapper.updateUserPoint(userId ,res);
            return true;
        }
        return false;
    }

    @Override
    public Long selectCount() {
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delete_status",0);
        return taskMapper.selectCount(queryWrapper);
    }


}
