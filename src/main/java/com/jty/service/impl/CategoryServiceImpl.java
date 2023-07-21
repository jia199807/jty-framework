package com.jty.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jty.domain.entity.Category;
import com.jty.domain.entity.vo.CategoryListVo;
import com.jty.mapper.CategoryMapper;
import com.jty.response.ResponseResult;
import com.jty.service.CategoryService;
import com.jty.system.SystemConstants;
import com.jty.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-07-21 04:46:54
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper
, Category> implements CategoryService {


    @Override
    public ResponseResult getCategoryList() {
        lambdaQuery().eq(Category::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Category> list = lambdaQuery().list();
        List<CategoryListVo> categoryListVos = BeanCopyUtils.copyBeanList(list, CategoryListVo.class);
        return ResponseResult.okResult(categoryListVos);
    }
}
