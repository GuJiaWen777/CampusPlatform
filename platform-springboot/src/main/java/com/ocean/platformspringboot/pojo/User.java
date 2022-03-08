package com.ocean.platformspringboot.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer logoff_status = 0;//注销标志（0正常 1注销）
    private Integer block_status = 0;//角色状态（0正常 1拉黑）

    public User(Integer id, String name, Integer gen, String pwd, String phone, String addr, String num, Integer point, Integer level) {
        this.id = id;
        this.name = name;
        this.gen = gen;
        this.pwd = pwd;
        this.phone = phone;
        this.addr = addr;
        this.num = num;
        this.point = point;
        this.level = level;
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
