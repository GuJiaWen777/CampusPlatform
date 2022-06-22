package com.ocean.platformspringboot.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.platformspringboot.entity.SchoolInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 校园信息表 Mapper 接口
 * </p>
 *
 * @author gjw
 * @since 2022-03-17
 */
public interface SchoolInfoMapper extends BaseMapper<SchoolInfo> {

    Page<SchoolInfo> findpage(Page<SchoolInfo> page, @Param("infoTitle") String infoTitle);

    Page<SchoolInfo> findpageCommon(Page<SchoolInfo> page,@Param("infoTitle")  String infoTitle);

    List<SchoolInfo> selectALL(@Param("infoTitle")  String infoTitle);

}
