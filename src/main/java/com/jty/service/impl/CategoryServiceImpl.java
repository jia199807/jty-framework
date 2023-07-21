package com.jty.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jty.domain.entity.Article;
import com.jty.domain.entity.Category;
import com.jty.domain.entity.vo.CategoryListVo;
import com.jty.mapper.CategoryMapper;
import com.jty.response.ResponseResult;
import com.jty.service.ArticleService;
import com.jty.service.CategoryService;
import com.jty.system.SystemConstants;
import com.jty.utils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-07-21 04:46:54
 */
@RequiredArgsConstructor
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper
, Category> implements CategoryService {

    private final ArticleService articleService;


    @Override
    public ResponseResult getCategoryList() {
        //查询所有状态正常的文章
        List<Article> articleList = articleService.lambdaQuery()
                .eq(Article::getStatus, SystemConstants.STATUS_NORMAL)
                .list();
        //提取分类id并去重，转换为List
        Set<Long> categoryIds = articleList
                .stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        List<Category> categories = listByIds(categoryIds);
        // 提取所有状态正常的分类
        List<Category> categoriesFiltered = categories
                .stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        // 封装返回
        List<CategoryListVo> categoryListVos = BeanCopyUtils.copyBeanList(categoriesFiltered, CategoryListVo.class);
        return ResponseResult.okResult(categoryListVos);
    }
}
