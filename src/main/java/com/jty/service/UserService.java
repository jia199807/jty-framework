package com.jty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jty.entity.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author OR
 * @since 2023-06-03
 */
public interface UserService extends IService<User> {

    User findByUsername(String email);

}
