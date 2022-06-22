package com.ocean.platformspringboot.service;

import com.ocean.platformspringboot.entity.TaskDone;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;

/**
 * <p>
 * 任务完成表 服务类
 * </p>
 *
 * @author gjw
 * @since 2022-03-16
 */
public interface ITaskDoneService extends IService<TaskDone> {

    Integer saveRecord(Integer taskId, Integer userId, LocalDateTime dateTime);
}
