package com.jty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jty.domain.entity.Comment;
import com.jty.response.ResponseResult;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-07-24 07:43:03
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);
}
