package com.jty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jty.entity.Token;
import com.jty.mapper.TokenMapper;
import com.jty.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author OR
 * @since 2023-06-03
 */
@RequiredArgsConstructor
@Service
@Transactional
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements TokenService {

    private final TokenMapper tokenMapper;

    @Override
    public Optional<Token> findValidTokenByUserId(Long userId) {
        Token res = tokenMapper.selectOne(
                new LambdaQueryWrapper<Token>()
                        .eq(Token::getUserId, userId)
                        .eq(Token::getExpired, false)
                        .eq(Token::getRevoked, false)
        );
        return Optional.ofNullable(res);
    }

    @Override
    public Optional<Token> findByToken(String token) {
        Token res = tokenMapper.selectOne(
                new LambdaQueryWrapper<Token>()
                        .eq(Token::getToken, token)
        );
        return Optional.ofNullable(res);
    }
}
