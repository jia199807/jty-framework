package com.jty.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jty.entity.Article;
import com.jty.mapper.ArticleMapper;
import com.jty.response.ResponseResult;
import com.jty.service.ArticleService;
import com.jty.service.CategoryService;
import com.jty.system.SystemConstants;
import com.jty.utils.BeanCopyUtils;
import com.jty.vo.ArticleDetailsVo;
import com.jty.vo.ArticleListVo;
import com.jty.vo.HotArticleVo;
import com.jty.vo.PageVo;
import jakarta.annotation.Resource;
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


        // 封装成ArticleListVo
        List<Article> articles = page.getRecords();

        //转换Vo，查询、复制分类名称
        CategoryService categoryService = getcategoryService();
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class)
                .stream()
                .map(articleListVo -> articleListVo.setCategoryName(categoryService.getById(articleListVo.getCategoryId()).getName()))
                .collect(Collectors.toList());;


        return ResponseResult.okResult(new PageVo(articleListVos,page.getTotal()));
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        // 根据id查文章
        Article article = getById(id);
        //转换Vo，查询、复制分类名称
        CategoryService categoryService = getcategoryService();
        ArticleDetailsVo articleDetailsVo = BeanCopyUtils.copyBean(article, ArticleDetailsVo.class);
        articleDetailsVo.setCategoryName(categoryService.getById(articleDetailsVo.getCategoryId()).getName());
        // 封装响应返回
        return ResponseResult.okResult(articleDetailsVo);
    }
}
