package com.jty.domain.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @program: blog
 * @description: 文章详情Vo
 * @author: 6420
 * @create: 2023-07-22 01:45
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ArticleDetailsVo {

    private Long id;
    //标题
    private String title;
    //所属分类id
    private Long categoryId;
    // 分类名称
    private String categoryName;
    //访问量
    private Long viewCount;
    // 创建时间
    private Date createTime;
    //文章内容
    private String content;
    //是否允许评论 1是，0否
    private String isComment;
}
