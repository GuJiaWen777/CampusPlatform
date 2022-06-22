package com.ocean.platformspringboot.mapper;

import com.ocean.platformspringboot.entity.TaskReceived;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * <p>
 * 任务接受表 Mapper 接口
 * </p>
 *
 * @author gjw
 * @since 2022-03-14
 */
public interface TaskReceivedMapper extends BaseMapper<TaskReceived> {
    Integer save(Integer taskId, Integer userId, LocalDateTime dateTime);
    Integer findUserByTask(@Param("taskId") Integer taskId);
    Integer selectCountByUser(@Param("userId") Integer userId);//查询接受任务总数
}
