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
 * 成就表
 * </p>
 *
 * @author gjw
 * @since 2022-03-27
 */
@Getter
@Setter
  public class Achievement implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 成就号
     */
      @TableId(value = "achv_id", type = IdType.AUTO)
      private Integer achvId;

      /**
     * 成就名称
     */
      private String achvName;

      /**
     * 成就描述
     */
      private String achvDescription;

      /**
     * 成就图片地址
     */
      private String picture;

      @TableField(exist = false)
      private Boolean owned = false;
      //true 已经获得 false未获得


}
