package com.ocean.platformspringboot.service.impl;

import com.ocean.platformspringboot.entity.TaskReceived;
import com.ocean.platformspringboot.mapper.TaskReceivedMapper;
import com.ocean.platformspringboot.service.ITaskReceivedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 任务接受表 服务实现类
 * </p>
 *
 * @author gjw
 * @since 2022-03-14
 */
@Service
public class TaskReceivedServiceImpl extends ServiceImpl<TaskReceivedMapper, TaskReceived> implements ITaskReceivedService {
    @Resource
    TaskReceivedMapper taskReceivedMapper;
    @Override
    public Integer saveRecord(Integer taskId, Integer userId, LocalDateTime dateTime) {
        return taskReceivedMapper.save(taskId,userId,dateTime);
    }

    @Override
    public Integer findReceivedId(Integer taskId) {
        return taskReceivedMapper.findUserByTask(taskId);
    }

}
