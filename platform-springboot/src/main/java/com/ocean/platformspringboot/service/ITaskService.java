package com.ocean.platformspringboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ocean.platformspringboot.entity.Task;


/**
 * <p>
 * 任务表 服务类
 * </p>
 *
 * @author gjw
 * @since 2022-03-09
 */

public interface ITaskService extends IService<Task> {


    Page<Task> findpage(Page<Task> page, String taskTitle);

    Page<Task> findpageCommon(Page<Task> page,String taskTitle);

    Page<Task> findReceivedTaskById(Page<Task> page, String taskTitle, Integer userId);

    Page<Task> findReleasedTaskById(Page<Task> page, String taskTitle, Integer userId);

    Integer addPointForUser(Integer userId, Integer taskId);

    Integer returnPointForUser(Integer userId, Integer taskId);

    Boolean deductPointForUser(Integer userId, Integer taskPoint);

    Long selectCount();


}
