package com.ocean.platformspringboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;//用户名
    private Integer gen;// 性别（0为女,1为男）

    @JsonIgnore
    private String pwd;
    private String phone;//电话
    private String addr;//地址
    private String num;//学号
    private Integer point = 0;//积分
    private Integer level = 0;//等级
    private Integer credit = 100;//信用值（上限100，优秀80~100，良好60~80，<60拉黑）
    private Integer logoff_status = 0;//注销标志（0正常 1注销）
    private Integer block_status = 0;//角色状态（0正常 1拉黑）
    private String avatar;//头像
    private Date createdTime;

    @TableField(exist = false)
    private Integer roleId;//角色ID


    public User(Integer id, String name, Integer gen, String pwd, String phone, String addr, String num, Integer point, Integer level, Integer credit) {
        this.id = id;
        this.name = name;
        this.gen = gen;
        this.pwd = pwd;
        this.phone = phone;
        this.addr = addr;
        this.num = num;
        this.point = point;
        this.level = level;
        this.credit = credit;
    }

    public User(String name, Integer gen, String pwd, String phone, String addr, String num) {
        this.name = name;
        this.gen = gen;
        this.pwd = pwd;
        this.phone = phone;
        this.addr = addr;
        this.num = num;
    }
}
