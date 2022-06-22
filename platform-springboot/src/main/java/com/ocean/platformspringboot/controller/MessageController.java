package com.ocean.platformspringboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocean.platformspringboot.common.Result;
import com.ocean.platformspringboot.service.IMessageService;
import com.ocean.platformspringboot.entity.Message;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 留言表 前端控制器
 * </p>
 *
 * @author gjw
 * @since 2022-03-31
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Resource
    private IMessageService messageService;

    @PostMapping
    public Result save(@RequestBody Message message) {
        LocalDateTime time1 = message.getSubmitTime().plusHours(8);
        message.setSubmitTime(time1);
        if(message.getReplyTime() != null){
            LocalDateTime time2 = message.getReplyTime().plusHours(8);
            message.setReplyTime(time2);
        }
        return Result.success(messageService.saveOrUpdate(message));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
            return Result.success(messageService.removeById(id));
    }

    @GetMapping
    public Result findAll() {
            return Result.success(messageService.list());
    }

    @GetMapping("/{id}")
    public  Result findOne(@PathVariable Integer id) {
            return Result.success(messageService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String type) {
        QueryWrapper <Message> queryWrapper=new QueryWrapper<>();
        if(!type.equals("")){
            queryWrapper.eq("type",Integer.parseInt(type));
        }
        return Result.success(messageService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    @GetMapping("/page/{id}")
    public Result findPageByUser(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @PathVariable("id") Integer userId) {
        QueryWrapper <Message> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return Result.success(messageService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

}
