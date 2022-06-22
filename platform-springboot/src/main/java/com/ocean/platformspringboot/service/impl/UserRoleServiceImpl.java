package com.ocean.platformspringboot.service.impl;

import com.ocean.platformspringboot.entity.UserRole;
import com.ocean.platformspringboot.mapper.UserRoleMapper;
import com.ocean.platformspringboot.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author gjw
 * @since 2022-03-25
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
