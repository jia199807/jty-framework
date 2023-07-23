package com.jty.service.impl;

import com.jty.domain.entity.LoginUser;
import com.jty.domain.entity.User;
import com.jty.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = null;
        // 根据用户名查询用户信息
        // try {
        user = userService.lambdaQuery()
                .eq(User::getUsername, username)
                .one();
        // }catch (Exception e){
        //     throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_EXIST);
        // }
        // 判断是否查到用户  如果没查到抛出异常
        // if (Objects.isNull(user)) {
        //     throw new RuntimeException("用户不存在");
        // }
        // 返回用户信息
        // TODO 查询权限信息封装
        return new LoginUser(user);
    }
}