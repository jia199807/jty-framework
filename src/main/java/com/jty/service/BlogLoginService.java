package com.jty.service;

import com.jty.domain.entity.User;
import com.jty.response.ResponseResult;

public interface BlogLoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}