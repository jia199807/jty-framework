package com.jty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jty.domain.entity.Article;
import com.jty.domain.entity.vo.HotArticleVo;
import com.jty.mapper.ArticleMapper;
import com.jty.response.ResponseResult;
import com.jty.service.ArticleService;
import com.jty.system.SystemConstants;
import com.jty.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-07-21 00:14:41
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper
, Article> implements ArticleService {

    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.STATUS_NORMAL);
        // 按照浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);

        Page<Article> page = new Page<>(1, 10);
        page(page,queryWrapper);
        List<Article> list = page.getRecords();

        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(list, HotArticleVo.class);
        return ResponseResult.okResult(hotArticleVos);
    }
}
