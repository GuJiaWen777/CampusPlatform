package com.ocean.platformspringboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.platformspringboot.common.Constants;
import com.ocean.platformspringboot.common.Result;
import com.ocean.platformspringboot.entity.Task;
import com.ocean.platformspringboot.service.ITaskService;
import com.ocean.platformspringboot.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 任务表 前端控制器
 * </p>
 *
 * @author gjw
 * @since 2022-03-09
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    @Resource
    private ITaskService taskService;
    @Resource
    private IUserService userService;

    //POST时对应的传参使用的是data，data是作为请求体发送的，同样使用这种形式的还有PUT,PATCH等请求方式
    //axios中POST的默认请求体类型为Content-Type:application/json
    //后台必须要以支持@RequestBody的形式接收参数

    //发布任务
    @PostMapping
    public Result save(@RequestBody Task task) {
        LocalDateTime time = task.getReleasedTime().plusHours(8);
        task.setReleasedTime(time);
        Integer point = task.getTaskPoints();
        if( point > 0){
            Integer userId = task.getReleasedBy();
            if(taskService.deductPointForUser(userId,point)){
                return Result.success(taskService.saveOrUpdate(task));
            }else {
                return  Result.error(Constants.CODE_606,"用户剩余积分不足，无法发布任务");
            }
        }
        return Result.success(taskService.saveOrUpdate(task));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
            return Result.success(taskService.removeById(id));
    }


    @GetMapping
    public Result findAll() {
            return Result.success(taskService.list());
    }

    @GetMapping("/{id}")
    public  Result findOne(@PathVariable("id") Integer id) {
            return Result.success(taskService.getById(id));
    }

    @GetMapping("/page")//管理员查询任务
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String taskTitle) {
        Page<Task> page = taskService.findpage(new Page<>(pageNum, pageSize),taskTitle);
        return Result.success(page);
    }

    @GetMapping("/page/common")//用户查询任务
    public Result findPageCommon(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(defaultValue = "") String taskTitle) {

        Page<Task> page = taskService.findpageCommon(new Page<>(pageNum, pageSize),taskTitle);
        return Result.success(page);
    }

    @PutMapping("/publish/{id}") //通过审核，发布任务
    public Result publishTask(@PathVariable("id") Integer id){
        Task one = taskService.getById(id);
        one.setCommitStatus(1);
        return Result.success(taskService.updateById(one));
    }

    @PutMapping("/return/{id}") //未通过审核，退回任务
    public Result returnTask(@PathVariable("id") Integer id){
        Task one = taskService.getById(id);
        one.setCommitStatus(2);
        taskService.returnPointForUser(one.getReleasedBy(),id);
        return Result.success(taskService.updateById(one));
    }
    @PutMapping("/cancel/{taskId}") //管理员终止任务
    public Result cancelTask(@PathVariable("taskId") Integer tackId){
        Task one = taskService.getById(tackId);
        one.setTaskStatus(3);
        return Result.success(taskService.updateById(one));
    }
    @PutMapping("/cancel/{taskId}/{userId}") //用户终止任务
    public Result cancelTask(@PathVariable("taskId") Integer taskId,
                             @PathVariable("userId") Integer userId){
        Task one = taskService.getById(taskId);
        if(!one.getReleasedBy().equals(userId)){//接受任务但是未完成任务，信用分降低
            userService.deductCredit(userId,one.getTaskPoints());
        }
        one.setTaskStatus(3);
        return Result.success(taskService.updateById(one));
    }
    @GetMapping("/released/{id}")//查询我发布的任务
    public Result findReleasedTask(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String taskTitle,
                                   @PathVariable("id") Integer userId) {
        Page<Task> page = taskService.findReleasedTaskById(new Page<>(pageNum, pageSize),taskTitle,userId);
        return Result.success(page);
    }
    @PutMapping("/del/{id}") //删除任务
    public Result deleteTask(@PathVariable("id") Integer id){
        Task one = taskService.getById(id);
        one.setDeleteStatus(1);
        return Result.success(taskService.updateById(one));
    }


}
