package com.jty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jty.domain.entity.User;
import com.jty.response.ResponseResult;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-07-22 03:44:28
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}
