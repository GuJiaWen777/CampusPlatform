package com.ocean.platformspringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocean.platformspringboot.entity.Notice;
import com.ocean.platformspringboot.mapper.NoticeMapper;
import com.ocean.platformspringboot.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 通知公告表 服务实现类
 * </p>
 *
 * @author gjw
 * @since 2022-03-27
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {
    @Resource
    NoticeMapper noticeMapper;

    @Override
    public List<Notice> selectNotice() {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("notice_id");
        queryWrapper.eq("notice_status",0);
        List<Notice> noticeList = noticeMapper.selectList(queryWrapper);
        Integer end= Math.min(noticeList.size(), 5);
        return noticeList.subList(0,end);
    }
}
