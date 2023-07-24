package com.jty.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jty.domain.entity.Comment;
import com.jty.domain.entity.User;
import com.jty.domain.entity.vo.CommentVo;
import com.jty.domain.entity.vo.PageVo;
import com.jty.mapper.CommentMapper;
import com.jty.response.ResponseResult;
import com.jty.service.CommentService;
import com.jty.service.UserService;
import com.jty.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-07-24 07:43:03
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper
        , Comment> implements CommentService {
    @Resource
    UserService userService;

    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        // 查询文章的所有根评论 （rootId=-1)
        Page<Comment> page = lambdaQuery()
                .eq(Comment::getArticleId, articleId)
                .eq(Comment::getRootId, -1)
                // 分页
                .page(new Page<>(pageNum, pageSize));
        List<Comment> commentList = page.getRecords();
        // 转换为CommentVo并为字段赋值
        List<CommentVo> commentVoList = toComentVoList(commentList);

        // 查询所有根评论对应的子评论集合，并且赋值给对应的属性
        List<CommentVo> commentVoWithChildrenList = commentVoList
                .stream()
                .map(commentVo -> commentVo.setChildren(
                        getChildren(commentVo.getId())
                ))
                .collect(Collectors.toList());
        return ResponseResult.okResult(new PageVo(commentVoWithChildrenList, page.getTotal()));
    }

    /**
     * 根据根评论的id查询所对应的子评论的集合
     *
     * @param id 根评论的id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        List<Comment> commentList = lambdaQuery()
                .eq(Comment::getRootId, id)
                .orderByAsc(Comment::getCreateTime)
                .list();
        List<CommentVo> commentVoList = toComentVoList(commentList);
        return commentVoList;
    }

    private List<CommentVo> toComentVoList(List<Comment> commentList) {
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);
        commentVoList.forEach(commentVo -> {
            // 获取发表评论的用户对象
            User user = userService.getById(commentVo.getCreateBy());
            // 将昵称作为用户名返回（因为前端写成用户名了，逻辑上来看应该是返回昵称）
            commentVo.setUsername(user.getNickName());

            // 对于非根评论
            if (commentVo.getRootId() != -1) {
                // 获取该评论所回复用户
                User toCommentUser = userService.getById(commentVo.getToCommentUserId());
                // 该评论所回复用户的用户名（因为前端写成用户名了，逻辑上来看应该是返回昵称）
                commentVo.setToCommentUserName(toCommentUser.getUsername());
            }
        });
        return commentVoList;
    }


}
