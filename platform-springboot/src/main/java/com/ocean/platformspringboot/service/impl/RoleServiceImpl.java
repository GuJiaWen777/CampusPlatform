package com.ocean.platformspringboot.service.impl;

import com.ocean.platformspringboot.entity.Role;
import com.ocean.platformspringboot.mapper.RoleMapper;
import com.ocean.platformspringboot.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author gjw
 * @since 2022-03-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
