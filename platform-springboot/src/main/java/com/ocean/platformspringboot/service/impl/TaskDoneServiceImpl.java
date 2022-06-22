package com.ocean.platformspringboot.service.impl;

import com.ocean.platformspringboot.entity.TaskDone;
import com.ocean.platformspringboot.mapper.TaskDoneMapper;
import com.ocean.platformspringboot.service.ITaskDoneService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 任务完成表 服务实现类
 * </p>
 *
 * @author gjw
 * @since 2022-03-16
 */
@Service
public class TaskDoneServiceImpl extends ServiceImpl<TaskDoneMapper, TaskDone> implements ITaskDoneService {
    @Resource
    TaskDoneMapper taskDoneMapper;
    @Override
    public Integer saveRecord(Integer taskId, Integer userId, LocalDateTime dateTime) {
        TaskDone taskDone = new TaskDone(userId,taskId,dateTime);
        return taskDoneMapper.insert(taskDone);
    }
}
