package com.jty.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jty.domain.entity.Article;
import com.jty.domain.entity.Category;
import com.jty.domain.entity.vo.ArticleVo;
import com.jty.domain.entity.vo.HotArticleVo;
import com.jty.domain.entity.vo.PageVo;
import com.jty.mapper.ArticleMapper;
import com.jty.response.ResponseResult;
import com.jty.service.ArticleService;
import com.jty.service.CategoryService;
import com.jty.system.SystemConstants;
import com.jty.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-07-21 00:14:41
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper
, Article> implements ArticleService {

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 使用方法返回实例对象，替换成员变量注入
     * @return ITbStaffService
     */
    public CategoryService getcategoryService(){
        return applicationContext.getBean(CategoryServiceImpl.class);
    }


    @Override
    public ResponseResult hotArticleList() {
        List<Article> list = lambdaQuery()
                // 必须是正式文章
                .eq(Article::getStatus, SystemConstants.STATUS_NORMAL)
                // 按照浏览量排序
                .orderByDesc(Article::getViewCount)
                .list()
                .stream()
                .limit(10)
                .toList();

        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(list, HotArticleVo.class);
        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {

        // LambdaQueryChainWrapper<Article> wrapper =
        Page<Article> page = lambdaQuery()
                // 如果 有categoryId 就只查对应分类的文章
                .eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId)
                // 正常状态的文章
                .eq(Article::getStatus, SystemConstants.STATUS_NORMAL)
                // 置顶
                .orderByDesc(Article::getIsTop)
                .page(new Page<>(pageNum, pageSize));


        // 封装成ArticleVo
        List<Article> articles = page.getRecords();
        List<ArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, ArticleVo.class);
        //查询categoryName
        CategoryService categoryService = getcategoryService();
        List<ArticleVo> articleVoList = articleVos.stream()
                .map(articleVo -> articleVo.setCategoryName(categoryService.getById(articleVo.getCategoryId()).getName()))
                .collect(Collectors.toList());

        return ResponseResult.okResult(new PageVo(articleVoList,page.getTotal()));
    }
}
