package com.jty.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jty.domain.entity.User;
import com.jty.mapper.UserMapper;
import com.jty.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-07-22 03:44:28
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper
        , User> implements UserService {


}
