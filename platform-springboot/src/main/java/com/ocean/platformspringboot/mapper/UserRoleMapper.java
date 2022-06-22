package com.ocean.platformspringboot.mapper;

import com.ocean.platformspringboot.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author gjw
 * @since 2022-03-25
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    Integer updateByUser(@Param("userId") Integer userId, @Param("roleId") Integer roleId);
    Integer selectUserCount(@Param("userId") Integer userId);
    Integer selectRoleByUser(@Param("userId") Integer userId);

}
