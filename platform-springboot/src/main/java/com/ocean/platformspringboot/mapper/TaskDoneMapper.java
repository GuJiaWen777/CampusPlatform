package com.ocean.platformspringboot.mapper;

import com.ocean.platformspringboot.entity.TaskDone;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * <p>
 * 任务完成表 Mapper 接口
 * </p>
 *
 * @author gjw
 * @since 2022-03-16
 */
public interface TaskDoneMapper extends BaseMapper<TaskDone> {
    Integer selectNoPointCount(@Param("userId") Integer userId);//查询完成的无积分任务总数
    Integer selectCountByUser(@Param("userId") Integer userId);//查询完成的无积分任务总数


}
