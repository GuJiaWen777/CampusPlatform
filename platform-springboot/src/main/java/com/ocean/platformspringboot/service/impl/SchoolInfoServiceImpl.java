package com.ocean.platformspringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.platformspringboot.entity.Review;
import com.ocean.platformspringboot.entity.SchoolInfo;
import com.ocean.platformspringboot.mapper.ReviewMapper;
import com.ocean.platformspringboot.mapper.SchoolInfoMapper;
import com.ocean.platformspringboot.service.ISchoolInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 校园信息表 服务实现类
 * </p>
 *
 * @author gjw
 * @since 2022-03-17
 */
@Service
public class SchoolInfoServiceImpl extends ServiceImpl<SchoolInfoMapper, SchoolInfo> implements ISchoolInfoService {
    @Resource
    SchoolInfoMapper schoolInfoMapper;
    @Resource
    ReviewMapper reviewMapper;
    @Override
    public Page<SchoolInfo> findpage(Page<SchoolInfo> page, String infoTitle) {
        return schoolInfoMapper.findpage(page,infoTitle);
    }
    @Override
    public Page<SchoolInfo>findpageCommon(Page<SchoolInfo> page, String infoTitle) {
        return schoolInfoMapper.findpageCommon(page,infoTitle);
    }


    @Override
    public Map<String,Object> getInfoWithReview(Integer pageNum,Integer pageSize,String infoTitle) {
        List<SchoolInfo> infoList = schoolInfoMapper.selectALL(infoTitle);
        Integer total = infoList.size();
        for (SchoolInfo info: infoList) {
            Integer infoId = info.getInfoId();
            List<Review> reply  = reviewMapper.selectByInfo(infoId);
            info.setReply(reply);
        }
        int i,j=0;
        List<SchoolInfo> queryList = new ArrayList<>();
        int start =  (pageNum -1) * pageSize ;
        int end = pageNum * pageSize - 1;
        for(i = start ;i <= end;i++){
            if(i < total){
                queryList.add(j,infoList.get(i));
                j++;
            }else {
                break;
            }
        }
        Map<String,Object> res = new HashMap<>();
        res.put("records",queryList);
        res.put("total",total);
        return res;
    }

    @Override
    public Integer selectCount() {
        return schoolInfoMapper.selectList(null).size();
    }

}
