package com.ocean.platformspringboot.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.platformspringboot.common.Result;
import com.ocean.platformspringboot.entity.Task;
import com.ocean.platformspringboot.service.ITaskService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import com.ocean.platformspringboot.service.ITaskReceivedService;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 任务接受表 前端控制器
 * </p>
 *
 * @author gjw
 * @since 2022-03-14
 */
@RestController
@RequestMapping("/task-received")
public class TaskReceivedController {
    @Resource
    private ITaskReceivedService ReceivedService;
    @Resource
    private ITaskService taskService;


    @PutMapping
    public Result receiveTask(@RequestBody String jsonData){
        JSONObject jsonObject = JSONUtil.parseObj(jsonData);
        JSONObject idsObject = (JSONObject) jsonObject.get("data");
        Integer taskId = (Integer) idsObject.get("taskId");
        Integer userId = (Integer) idsObject.get("userId");
        LocalDateTime dateTime = LocalDateTime.now();
        Task one = taskService.getById(taskId);
        one.setTaskStatus(1);
        taskService.updateById(one);
        return Result.success(ReceivedService.saveRecord(taskId,userId,dateTime));
    }
    @GetMapping("/{id}")
    public Result receiveTask(@RequestParam("pageNum") Integer pageNum,
                              @RequestParam("pageSize") Integer pageSize,
                              @RequestParam(defaultValue = "") String taskTitle,
                              @PathVariable("id") Integer userId){
        Page<Task> page = taskService.findReceivedTaskById(new Page<>(pageNum, pageSize),taskTitle,userId);
        return Result.success(page);
    }

}
