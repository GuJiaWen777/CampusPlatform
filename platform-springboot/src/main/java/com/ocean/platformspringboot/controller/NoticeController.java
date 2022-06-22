package com.ocean.platformspringboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocean.platformspringboot.common.Result;
import com.ocean.platformspringboot.service.INoticeService;
import com.ocean.platformspringboot.entity.Notice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 通知公告表 前端控制器
 * </p>
 *
 * @author gjw
 * @since 2022-03-27
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    private INoticeService noticeService;

    @PostMapping
    public Result save(@RequestBody Notice notice) {
            return Result.success(noticeService.saveOrUpdate(notice));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
            return Result.success(noticeService.removeById(id));
    }


    @GetMapping
    public Result findAll() {
        List<Notice> noticeList = noticeService.selectNotice();
        return Result.success(noticeList);
    }

    @GetMapping("/{id}")
    public  Result findOne(@PathVariable Integer id) {
            return Result.success(noticeService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String noticeTitle) {
        QueryWrapper <Notice> queryWrapper=new QueryWrapper<>();
        if(!"".equals(noticeTitle)){
            queryWrapper.like("notice_title",noticeTitle);
        }
        return Result.success(noticeService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }
    @PutMapping("/status/{id}/{status}")
    public Result updateStatus(@PathVariable("id") Integer noticeId,
                               @PathVariable("status") Integer noticeStatus){
        Notice one = noticeService.getById(noticeId);
        one.setNoticeStatus(noticeStatus);
        return Result.success(noticeService.updateById(one));
    }

}
