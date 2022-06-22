package com.ocean.platformspringboot.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author gjw
 * @since 2022-03-25
 */
@Getter
@Setter
  public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 角色号
     */
      @TableId(value = "role_id", type = IdType.AUTO)
      private Integer roleId;

      /**
     * 角色名称
     */
      private String roleName;

      /**
     * 角色权限字符串
     */
      private String roleKey;

      /**
     * 角色状态;0正常 1停用
     */
      private Integer blockStatus;

      /**
     * 删除标志;0代表存在 1代表删除
     */
      private Integer deleteStatus;



}
