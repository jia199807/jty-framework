package com.jty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jty.entity.Article;
import org.apache.ibatis.annotations.Mapper;


/**
 * 文章表(Article)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-21 00:14:43
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
