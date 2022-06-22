package com.ocean.platformspringboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 任务完成表
 * </p>
 *
 * @author gjw
 * @since 2022-03-16
 */
@Getter
@Setter
@AllArgsConstructor
@TableName("task_done")
public class TaskDone implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 用户号
     */
        private Integer userId;

      /**
     * 任务号
     */
        private Integer taskId;

      /**
     * 完成任务时间
     */
      private LocalDateTime taskDoneTime;


}
