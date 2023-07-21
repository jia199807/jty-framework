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

    /**
     * @author 6420
     * @description ①要求只展示有发布正式文章的分类 ②必须是正常状态的分类
     * @date 10:30 2023/7/21
     * @return com.jty.response.ResponseResult
     **/
    ResponseResult getCategoryList();
}
