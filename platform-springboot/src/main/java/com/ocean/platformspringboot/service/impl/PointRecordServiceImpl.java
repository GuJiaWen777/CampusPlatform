package com.ocean.platformspringboot.service.impl;

import com.ocean.platformspringboot.entity.PointRecord;
import com.ocean.platformspringboot.mapper.PointRecordMapper;
import com.ocean.platformspringboot.service.IPointRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 积分记录表 服务实现类
 * </p>
 *
 * @author gjw
 * @since 2022-03-18
 */
@Service
public class PointRecordServiceImpl extends ServiceImpl<PointRecordMapper, PointRecord> implements IPointRecordService {

}
