package com.ocean.platformspringboot.service;

import com.ocean.platformspringboot.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 通知公告表 服务类
 * </p>
 *
 * @author gjw
 * @since 2022-03-27
 */
public interface INoticeService extends IService<Notice> {

    List<Notice> selectNotice();

}
