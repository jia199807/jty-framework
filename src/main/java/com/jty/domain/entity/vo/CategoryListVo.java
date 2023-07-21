package com.jty.domain.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: blog
 * @description: 分类列表中的category
 * @author: 6420
 * @create: 2023-07-21 10:12
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListVo {

    private Long id;
    // 分类名
    private String name;
}
