package com.ocean.platformspringboot.service;

import com.ocean.platformspringboot.entity.TaskReceived;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;

/**
 * <p>
 * 任务接受表 服务类
 * </p>
 *
 * @author gjw
 * @since 2022-03-14
 */
public interface ITaskReceivedService extends IService<TaskReceived> {

    Integer saveRecord(Integer taskId, Integer userId, LocalDateTime dateTime);

    Integer findReceivedId(Integer taskId);

}
