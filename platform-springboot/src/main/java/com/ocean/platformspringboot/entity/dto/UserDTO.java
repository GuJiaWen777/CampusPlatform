package com.ocean.platformspringboot.entity.dto;

import com.ocean.platformspringboot.entity.Menu;
import lombok.Data;

import java.util.List;

/*
接受前端请求数据
 */
@Data
public class UserDTO {
    private Integer id;//id
    private String name;//用户名
    private String pwd;//密码
    private String num;//学号
    private Integer gen;// 性别（0为女,1为男）
    private String phone;//电话
    private String addr;//地址
    private String avatar;//头像
    private String token;
    private Integer roleId;
    private List<Menu> menus;
}
