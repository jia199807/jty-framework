package com.jty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jty.entity.Link;
import com.jty.response.ResponseResult;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-07-22 02:49:37
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
