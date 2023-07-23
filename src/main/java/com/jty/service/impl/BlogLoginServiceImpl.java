package com.jty.service.impl;

import com.jty.domain.entity.LoginUser;
import com.jty.domain.entity.User;
import com.jty.domain.entity.vo.BlogUserLoginVo;
import com.jty.domain.entity.vo.UserInfoVo;
import com.jty.response.ResponseResult;
import com.jty.service.BlogLoginService;
import com.jty.utils.BeanCopyUtils;
import com.jty.utils.JwtUtil;
import com.jty.utils.RedisCache;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("blogLoginService")
public class BlogLoginServiceImpl implements BlogLoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;


    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);


        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        String userId = loginUser.getUser().getId().toString();

        String jwtToken = JwtUtil.createJWT(userId);
        // 把用户信息存入redis
        redisCache.setCacheObject("bloglogin:" + userId, loginUser);

        // 把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);

        // 把token和userInfoVo封装 返回
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwtToken, userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }
}