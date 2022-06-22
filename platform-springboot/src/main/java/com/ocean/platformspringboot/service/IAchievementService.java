package com.ocean.platformspringboot.service;

import com.ocean.platformspringboot.entity.Achievement;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 成就表 服务类
 * </p>
 *
 * @author gjw
 * @since 2022-03-27
 */
public interface IAchievementService extends IService<Achievement> {


    Map<String, Object> getByUserId(Integer pageNum, Integer pageSize, Integer userId);

    Boolean finishAchievement(Integer achvId, Integer userId);

}
