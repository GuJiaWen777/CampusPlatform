package com.ocean.platformspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 任务表
 * </p>
 *
 * @author gjw
 * @since 2022-03-09
 */
@Getter
@Setter
  public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 任务号
     */
      @TableId(value = "task_id", type = IdType.AUTO)
      private Integer taskId;

      /**
     * 任务标题
     */
      private String taskTitle;

      /**
     * 任务描述
     */
      private String taskDescription;

      /**
     * 任务奖励积分
     */
      private Integer taskPoints;

      /**
     * 任务运行状态;0为待接受，1为进行中，2为已完成，3为已终止（用户可以看见，但无法接受）
     */
      private Integer taskStatus;

      /**
     * 发布任务状态;0为审核中，1为已通过,2为未通过
     */
      private Integer commitStatus;

      /**
     * 是否被删除;0为未删除，1为已删除
     */
      private Integer deleteStatus;

      /**
     * 发布人ID
     */
      private Integer releasedBy;

      /**
     * 发布时间
     */
      private LocalDateTime releasedTime;


      @TableField(exist = false)
      private String releasedName;//发布人姓名
      @TableField(exist = false)
      private String receivedName;//接受人姓名
      @TableField(exist = false)
      private String doneName;//接受人姓名
}
