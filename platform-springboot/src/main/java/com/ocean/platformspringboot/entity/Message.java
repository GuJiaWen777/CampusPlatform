package com.ocean.platformspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 留言表
 * </p>
 *
 * @author gjw
 * @since 2022-03-31
 */
@Getter
@Setter
  public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 留言号
     */
      @TableId(value = "message_id", type = IdType.AUTO)
      private Integer messageId;
      /**
      * 发布人
      */
      private Integer userId;

      /**
     * 提交时间
     */
      private LocalDateTime submitTime;

      /**
     * 问题类型
     */
      private Integer type;

      /**
     * 处理状态;0待回复，1已回复
     */
      private Integer status;

      /**
     * 情况说明
     */
      private String description;

      /**
     * 客服回复时间
     */
      private LocalDateTime replyTime;

      /**
     * 客服回复
     */
      private String reply;
      /**
       * 联系方式
       */
      private String contact;



}
