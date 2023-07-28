package com.jty.service;

import com.jty.entity.User;
import com.jty.response.ResponseResult;
import jakarta.servlet.http.HttpSession;

public interface BlogLoginService {

    ResponseResult login(User user, HttpSession session);

    ResponseResult logout();
}