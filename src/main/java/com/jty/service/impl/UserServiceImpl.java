package com.jty.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jty.domain.entity.User;
import com.jty.domain.entity.vo.UserInfoVo;
import com.jty.mapper.UserMapper;
import com.jty.response.ResponseResult;
import com.jty.service.UserService;
import com.jty.utils.BeanCopyUtils;
import com.jty.utils.SecurityUtils;
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


    @Override
    public ResponseResult userInfo() {
        // 获取当前用户id
        Long userId = SecurityUtils.getUserId();
        // 根据用户id查询用户信息
        User user = getById(userId);
        // 封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }
}
