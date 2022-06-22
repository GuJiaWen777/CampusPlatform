package com.ocean.platformspringboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import com.ocean.platformspringboot.common.Result;
import com.ocean.platformspringboot.service.IReviewService;
import com.ocean.platformspringboot.entity.Review;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author gjw
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/review")
public class ReviewController {
    @Resource
    private IReviewService reviewService;

    @PostMapping
    public Result save(@RequestBody Review review) {
        LocalDateTime time = review.getReviewTime().plusHours(8);
        review.setReviewTime(time);
        return Result.success(reviewService.saveOrUpdate(review));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
            return Result.success(reviewService.removeById(id));
    }


    @GetMapping
    public Result findAll() {
            return Result.success(reviewService.list());
    }

    @GetMapping("/{id}")
    public  Result findOne(@PathVariable Integer id) {
            return Result.success(reviewService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String reviewDescription) {
        Page<Review> page = reviewService.findpage(new Page<>(pageNum, pageSize),reviewDescription);
        return Result.success(page);
    }

}
