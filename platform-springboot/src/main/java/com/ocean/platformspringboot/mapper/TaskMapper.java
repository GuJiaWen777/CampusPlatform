package com.ocean.platformspringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.platformspringboot.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 任务表 Mapper 接口
 * </p>
 *
 * @author gjw
 * @since 2022-03-09
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

    Page<Task> findpage(Page<Task> page, @Param("taskTitle") String taskTitle);

    Page<Task> findpageCommon(Page<Task> page, @Param("taskTitle") String taskTitle);

    //我接受的
    Page<Task> findReceivedTaskById(Page<Task> page, @Param("taskTitle") String taskTitle, @Param("userId") Integer userId);
    //我发布的
    Page<Task> findReleasedTaskById(Page<Task> page, @Param("taskTitle") String taskTitle, @Param("userId") Integer userId);

}
