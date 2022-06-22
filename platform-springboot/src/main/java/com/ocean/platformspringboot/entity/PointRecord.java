package com.ocean.platformspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 积分记录表
 * </p>
 *
 * @author gjw
 * @since 2022-03-18
 */
@Getter
@Setter
@TableName("point_record")
public class PointRecord implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 记录号
     */
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 用户号
     */
      private Integer userId;

      /**
     * 积分更改数量
     */
      private Integer pointNum;

      /**
     * 积分更改描述
     */
      private String alterDescription;

      /**
     * 记录时间
     */
      private LocalDateTime recordTime;


}
