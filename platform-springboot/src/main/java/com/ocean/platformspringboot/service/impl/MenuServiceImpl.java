package com.ocean.platformspringboot.service.impl;

import com.ocean.platformspringboot.entity.Menu;
import com.ocean.platformspringboot.mapper.MenuMapper;
import com.ocean.platformspringboot.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author gjw
 * @since 2022-03-26
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
