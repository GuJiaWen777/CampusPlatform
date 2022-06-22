package com.ocean.platformspringboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDateTime;

import java.util.Map;

import com.ocean.platformspringboot.common.Result;
import com.ocean.platformspringboot.service.ISchoolInfoService;
import com.ocean.platformspringboot.entity.SchoolInfo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 校园信息表 前端控制器
 * </p>
 *
 * @author gjw
 * @since 2022-03-17
 */
@RestController
@RequestMapping("/info")
public class SchoolInfoController {
    @Resource
    private ISchoolInfoService schoolInfoService;

    @PostMapping
    public Result save(@RequestBody SchoolInfo schoolInfo) {
        LocalDateTime time = schoolInfo.getReleasedTime().plusHours(8);
        schoolInfo.setReleasedTime(time);
        return Result.success(schoolInfoService.saveOrUpdate(schoolInfo));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
            return Result.success(schoolInfoService.removeById(id));
    }


    @GetMapping
    public Result findAll() {
            return Result.success(schoolInfoService.list());
    }

    @GetMapping("/{id}")
    public  Result findOne(@PathVariable Integer id) {
            return Result.success(schoolInfoService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String infoTitle) {
        Page<SchoolInfo> page = schoolInfoService.findpage(new Page<>(pageNum,pageSize),infoTitle);
        return Result.success(page);
    }
    @GetMapping("/page/common")
    public Result findPageCommon(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(defaultValue = "") String infoTitle) {
        Page<SchoolInfo> page = schoolInfoService.findpageCommon(new Page<>(pageNum,pageSize),infoTitle);
        return Result.success(page);
    }
    @GetMapping("/page/review")
    public Result findReview(@RequestParam Integer pageNum,
                             @RequestParam Integer pageSize,
                             @RequestParam(defaultValue = "") String infoTitle) {
        Map<String,Object> res  = schoolInfoService.getInfoWithReview(pageNum,pageSize,infoTitle);
        return Result.success(res);
    }

    //通过审核，发布信息
    @PutMapping("/publish/{id}")
    public Result publishInfo(@PathVariable("id") Integer id){
        SchoolInfo one = schoolInfoService.getById(id);
        one.setInfoStatus(1);
        return Result.success(schoolInfoService.updateById(one));
    }

}
