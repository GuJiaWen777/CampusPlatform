package com.ocean.platformspringboot.service.impl;

import com.ocean.platformspringboot.entity.Message;
import com.ocean.platformspringboot.mapper.MessageMapper;
import com.ocean.platformspringboot.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 留言表 服务实现类
 * </p>
 *
 * @author gjw
 * @since 2022-03-31
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
