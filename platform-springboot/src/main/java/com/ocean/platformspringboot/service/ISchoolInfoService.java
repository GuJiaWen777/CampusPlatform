package com.ocean.platformspringboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.platformspringboot.entity.SchoolInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 校园信息表 服务类
 * </p>
 *
 * @author gjw
 * @since 2022-03-17
 */
public interface ISchoolInfoService extends IService<SchoolInfo> {

    Page<SchoolInfo> findpage(Page<SchoolInfo> page, String infoTitle);

    Page<SchoolInfo> findpageCommon(Page<SchoolInfo> page, String infoTitle);

    Map<String,Object> getInfoWithReview(Integer pageNum, Integer pageSize, String infoTitle);

    Integer selectCount();

}
