package com.jty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jty.domain.entity.Category;
import com.jty.response.ResponseResult;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-07-21 04:46:53
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
