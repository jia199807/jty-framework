package com.jty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jty.entity.User;
import com.jty.mapper.UserMapper;
import com.jty.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-07-28 00:47:22
 */
@RequiredArgsConstructor
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper
        , User> implements UserService {
    private final UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username)
        );
    }
}
