package com.ocean.platformspringboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author gjw
 * @since 2022-03-25
 */
@Getter
@Setter
@AllArgsConstructor
@TableName("user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 用户号
     */
      private Integer userId;

      /**
     * 角色号
     */
      private Integer roleId;


}
