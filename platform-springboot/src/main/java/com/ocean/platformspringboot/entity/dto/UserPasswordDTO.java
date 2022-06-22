package com.ocean.platformspringboot.entity.dto;

import lombok.Data;

@Data
public class UserPasswordDTO {
    private String name;
    private String pwd;
    private String newPassword;
}
