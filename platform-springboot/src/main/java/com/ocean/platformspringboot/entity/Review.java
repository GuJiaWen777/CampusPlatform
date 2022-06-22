package com.ocean.platformspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author gjw
 * @since 2022-03-18
 */
@Getter
@Setter
  public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 评论号
     */
      @TableId(value = "review_id", type = IdType.AUTO)
      private Integer reviewId;

      /**
     * 信息号
     */
      private Integer infoId;

      /**
     * 用户号
     */
      private Integer userId;

      /**
     * 评论内容
     */
      private String reviewDescription;

      /**
     * 评论时间
     */
      private LocalDateTime reviewTime;

      /**
     * 是否被删除;0为未删除，1未已删除
     */
      private Integer deleteStatus;
      @TableField(exist = false)
      private String userName;
      @TableField(exist = false)
      private String avatar;
      @TableField(exist = false)
      private String infoTitle;


}
