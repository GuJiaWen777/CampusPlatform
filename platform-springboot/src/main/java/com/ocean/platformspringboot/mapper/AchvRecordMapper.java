package com.ocean.platformspringboot.mapper;

import com.ocean.platformspringboot.entity.AchvRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 成就记录表 Mapper 接口
 * </p>
 *
 * @author gjw
 * @since 2022-03-27
 */
public interface AchvRecordMapper extends BaseMapper<AchvRecord> {

    List<Integer> selectAchieveIds(@Param("userId") Integer userId);

}
