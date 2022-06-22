package com.ocean.platformspringboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.ocean.platformspringboot.common.Result;
import com.ocean.platformspringboot.entity.Task;
import com.ocean.platformspringboot.entity.User;
import com.ocean.platformspringboot.service.IRewardService;
import com.ocean.platformspringboot.service.ISchoolInfoService;
import com.ocean.platformspringboot.service.ITaskService;
import com.ocean.platformspringboot.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Resource
    private IUserService userService;
    @Resource
    private ITaskService taskService;
    @Resource
    private ISchoolInfoService infoService;
    @Resource
    private IRewardService rewardService;
    @GetMapping("/members")
    public Result members() {
        List<User> list = userService.getAll();
        int q1 = 0; // 第一季度
        int q2 = 0; // 第二季度
        int q3 = 0; // 第三季度
        int q4 = 0; // 第四季度
        for (User user : list) {
            Date createdTime = user.getCreatedTime();
            Quarter quarter = DateUtil.quarterEnum(createdTime);
            switch (quarter) {
                case Q1: q1 += 1; break;
                case Q2: q2 += 1; break;
                case Q3: q3 += 1; break;
                case Q4: q4 += 1; break;
                default: break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }
    @GetMapping("/tasks")
    public Result tasks() {
        List<Task> list = taskService.list();
        int q1 = 0; // 第一季度
        int q2 = 0; // 第二季度
        int q3 = 0; // 第三季度
        int q4 = 0; // 第四季度
        for (Task task : list) {
            ZonedDateTime zonedDateTime = task.getReleasedTime().atZone(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            Date createdTime = Date.from(instant);
            Quarter quarter = DateUtil.quarterEnum(createdTime);
            switch (quarter) {
                case Q1: q1 += 1; break;
                case Q2: q2 += 1; break;
                case Q3: q3 += 1; break;
                case Q4: q4 += 1; break;
                default: break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }
    @GetMapping("/statistics")
    public Result statistics() {
        Integer userCount = userService.selectCount();
        Integer taskCount = Math.toIntExact(taskService.selectCount());
        Integer infoCount = infoService.selectCount();
        Integer rewardCount = rewardService.selectCount();
        Map<String, Object> map = new HashMap();
        map.put("user",userCount);
        map.put("task",taskCount);
        map.put("info",infoCount);
        map.put("reward",rewardCount);
        return Result.success(map);
    }
}
