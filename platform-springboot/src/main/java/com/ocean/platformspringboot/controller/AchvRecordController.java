package com.ocean.platformspringboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocean.platformspringboot.common.Result;
import com.ocean.platformspringboot.service.IAchvRecordService;
import com.ocean.platformspringboot.entity.AchvRecord;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 成就记录表 前端控制器
 * </p>
 *
 * @author gjw
 * @since 2022-03-27
 */
@RestController
@RequestMapping("/achv-record")
public class AchvRecordController {
    @Resource
    private IAchvRecordService achvRecordService;

    @PostMapping
    public Result save(@RequestBody AchvRecord achvRecord) {
            return Result.success(achvRecordService.saveOrUpdate(achvRecord));
    }

    @GetMapping
    public Result findAll() {
            return Result.success(achvRecordService.list());
    }

    @GetMapping("/{id}")
    public  Result findOne(@PathVariable Integer id) {
            return Result.success(achvRecordService.getById(id));
    }



}
