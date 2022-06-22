package com.ocean.platformspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 校园信息表
 * </p>
 *
 * @author gjw
 * @since 2022-03-17
 */
@Getter
@Setter
@TableName("school_info")
public class SchoolInfo implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 信息号
     */
      @TableId(value = "info_id", type = IdType.AUTO)
      private Integer infoId;

      /**
     * 信息标题
     */
      private String infoTitle;

      /**
     * 信息描述
     */
      private String infoDescription;

      /**
     * 发布者ID
     */
      private Integer releasedBy;

      /**
     * 信息发布时间
     */
      private LocalDateTime releasedTime;

      /**
     * 信息状态;0为未发布，1未已发布
     */
      private Integer infoStatus;
      @TableField(exist = false)
      private String releasedName;//发布人姓名
      @TableField(exist = false)
      private List<Review> reply;//信息的评论回复
      @TableField(exist = false)
      private Boolean inputShow = false;
      @TableField(exist = false)
      private String avatar;


}
