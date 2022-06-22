package com.ocean.platformspringboot.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ocean.platformspringboot.entity.Task;
import com.ocean.platformspringboot.service.ITaskReceivedService;
import com.ocean.platformspringboot.service.ITaskService;
import com.ocean.platformspringboot.service.IUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import com.ocean.platformspringboot.common.Result;
import com.ocean.platformspringboot.service.ITaskDoneService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 任务完成表 前端控制器
 * </p>
 *
 * @author gjw
 * @since 2022-03-16
 */
@RestController
@RequestMapping("/task-done")
public class TaskDoneController {
    @Resource
    private ITaskDoneService taskDoneService;
    @Resource
    private ITaskService taskService;
    @Resource
    private IUserService userService;
    @Resource
    private ITaskReceivedService taskReceivedService;

    //完成任务,获得任务奖励积分和等级经验
    @Transactional
    @PutMapping
    public Result doneTask(@RequestBody String jsonData){
        JSONObject jsonObject = JSONUtil.parseObj(jsonData);
        JSONObject idsObject = (JSONObject) jsonObject.get("data");
        Integer taskId = (Integer) idsObject.get("taskId");
        Integer userId = taskReceivedService.findReceivedId(taskId);
        //Integer userId = (Integer) idsObject.get("userId");
        LocalDateTime dateTime = LocalDateTime.now();
        Task one = taskService.getById(taskId);
        one.setTaskStatus(2);
        taskService.updateById(one);
        taskService.addPointForUser(userId,taskId);//加积分
        userService.addLevelForUser(userId);//加经验
        userService.addCredit(userId);//加信用
        return Result.success(taskDoneService.saveRecord(taskId,userId,dateTime));
    }




}
