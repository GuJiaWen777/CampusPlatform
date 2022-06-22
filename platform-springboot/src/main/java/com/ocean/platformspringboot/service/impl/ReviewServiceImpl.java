package com.ocean.platformspringboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.platformspringboot.entity.Review;
import com.ocean.platformspringboot.mapper.ReviewMapper;
import com.ocean.platformspringboot.service.IReviewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author gjw
 * @since 2022-03-18
 */
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements IReviewService {
    @Resource
    ReviewMapper reviewMapper;
    @Override
    public Page<Review> findpage(Page<Review> page, String reviewDescription) {

        return reviewMapper.findpage(page,reviewDescription);
    }
}
