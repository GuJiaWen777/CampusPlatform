package com.ocean.platformspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author gjw
 * @since 2022-03-26
 */
@Getter
@Setter
  public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 菜单ID
     */
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 菜单名称
     */
      private String name;

      /**
     * 父菜单ID
     */
      private Integer pid;

      /**
     * 显示顺序
     */
      private Integer sortNum;

      /**
     * 路由地址
     */
      private String path;

      /**
     * 组件路径
     */
      private String pagePath;

      /**
     * 菜单图标
     */
      private String icon;

      @TableField(exist = false)
      private List<Menu> children;


}
