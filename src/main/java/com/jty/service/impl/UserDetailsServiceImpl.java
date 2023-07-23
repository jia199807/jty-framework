package com.jty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jty.domain.entity.LoginUser;
import com.jty.domain.entity.User;
import com.jty.enums.AppHttpCodeEnum;
import com.jty.handler.exception.SystemException;
import com.jty.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userService.getOne(queryWrapper);
        // }catch (Exception e){
        //     throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_EXIST);
        // }
        // 判断是否查到用户  如果没查到抛出异常
        if (Objects.isNull(user)) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_EXIST);
        }
        // 返回用户信息
        // TODO 查询权限信息封装
        return new LoginUser(user);
    }
}