package com.jty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jty.entity.Token;

import java.util.Optional;

;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author OR
 * @since 2023-06-03
 */
public interface TokenService extends IService<Token> {

    Optional<Token> findValidTokenByUserId(Long userId);


    Optional<Token> findByToken(String token);
}
