package com.jty.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jty.entity.Link;
import com.jty.mapper.LinkMapper;
import com.jty.response.ResponseResult;
import com.jty.service.LinkService;
import com.jty.system.SystemConstants;
import com.jty.utils.BeanCopyUtils;
import com.jty.vo.LinkVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-07-22 02:49:37
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper
, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        // 查出所有审核通过的友链
        List<Link> linkList = lambdaQuery()
                .eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL)
                .list();
        // 转换LinkVo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(linkList, LinkVo.class);

        // 封装响应返回
        return ResponseResult.okResult(linkVos);
    }
}
