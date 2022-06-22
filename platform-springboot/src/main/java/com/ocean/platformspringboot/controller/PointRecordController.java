package com.ocean.platformspringboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocean.platformspringboot.common.Result;
import com.ocean.platformspringboot.service.IPointRecordService;
import com.ocean.platformspringboot.entity.PointRecord;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 积分记录表 前端控制器
 * </p>
 *
 * @author gjw
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/point-record")
public class PointRecordController {
    @Resource
    private IPointRecordService pointRecordService;

    @PostMapping
    public Result save(@RequestBody PointRecord pointRecord) {
            return Result.success(pointRecordService.saveOrUpdate(pointRecord));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
            return Result.success(pointRecordService.removeById(id));
    }


    @GetMapping
    public Result findAll() {
            return Result.success(pointRecordService.list());
    }

    @GetMapping("/{id}")
    public  Result findOne(@PathVariable Integer id) {
            return Result.success(pointRecordService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper <PointRecord> queryWrapper=new QueryWrapper<>();
        return Result.success(pointRecordService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

}
