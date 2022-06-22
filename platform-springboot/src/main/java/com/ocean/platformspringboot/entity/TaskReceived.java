package com.ocean.platformspringboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 任务接受表
 * </p>
 *
 * @author gjw
 * @since 2022-03-14
 */
@Getter
@Setter
@TableName("task_received")
public class TaskReceived implements Serializable {

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
     * 接受任务时间
     */
      private LocalDateTime taskReceivedTime;


}
