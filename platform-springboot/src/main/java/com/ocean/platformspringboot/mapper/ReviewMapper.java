package com.ocean.platformspringboot.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.platformspringboot.entity.Review;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author gjw
 * @since 2022-03-18
 */
public interface ReviewMapper extends BaseMapper<Review> {


    List<Review> selectByInfo(@Param("infoId") Integer infoId);

    Page<Review> findpage( Page<Review> page, @Param("reviewDescription") String reviewDescription);

}
