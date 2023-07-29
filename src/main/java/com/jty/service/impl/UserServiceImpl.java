package com.jty.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jty.domain.entity.User;
import com.jty.domain.entity.vo.UserInfoVo;
import com.jty.enums.AppHttpCodeEnum;
import com.jty.handler.exception.SystemException;
import com.jty.mapper.UserMapper;
import com.jty.response.ResponseResult;
import com.jty.service.UserService;
import com.jty.utils.BeanCopyUtils;
import com.jty.utils.SecurityUtils;
import com.jty.utils.SpringContextUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        // 对数据进行非空判断
        if (!StringUtils.hasText(user.getUsername())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickname())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }

        // 对数据进行是否存在的判断
        if (usernameExist(user.getUsername())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (nicknameExist(user.getNickname())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }


        //...
        // 对密码进行加密
        PasswordEncoder passwordEncoder = (PasswordEncoder) SpringContextUtil.getBean("passwordEncoder");
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        // 存入数据库
        save(user);
        return ResponseResult.okResult();


    }

    private boolean nicknameExist(String nickName) {
        boolean b = lambdaQuery()
                .eq(User::getNickname, nickName)
                .exists();
        return b;
    }

    private boolean usernameExist(String username) {
        boolean b = lambdaQuery()
                .eq(User::getUsername, username)
                .exists();
        return b;
    }
}
