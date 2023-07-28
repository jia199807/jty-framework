package com.jty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jty.entity.Article;
import com.jty.response.ResponseResult;


/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2023-07-21 00:14:40
 */
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);
}
