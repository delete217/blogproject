package com.delete.blogg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.delete.blogg.entity.Comment;
import com.delete.blogg.vo.Result;
import com.delete.blogg.vo.params.CommentParams;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
public interface ICommentService extends IService<Comment> {

    Result commentsByArticleId(long id);

    Result addComment(CommentParams commentParams);
}
