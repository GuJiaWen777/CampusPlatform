package com.ocean.platformspringboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.platformspringboot.entity.Review;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author gjw
 * @since 2022-03-18
 */
public interface IReviewService extends IService<Review> {

    Page<Review> findpage( Page<Review> page, @Param("reviewDescription") String reviewDescription);

}
