package com.delete.blogg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.delete.blogg.entity.Comment;
import com.delete.blogg.entity.SysUser;
import com.delete.blogg.mapper.CommentMapper;
import com.delete.blogg.service.ICommentService;
import com.delete.blogg.service.ISysUserService;
import com.delete.blogg.utils.UserThreadLocal;
import com.delete.blogg.vo.CommentVo;
import com.delete.blogg.vo.Result;
import com.delete.blogg.vo.UserVo;
import com.delete.blogg.vo.params.CommentParams;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ISysUserService sysUserService;

    @Override
    public Result commentsByArticleId(long id) {
        /**
         * 1. 根据文章id 查询评论列表 comment表
         * 2. 根据作者id 查询作者信息
         * 3. 判断评论 level=1 要去查询有没有子评论
         * 4. 如果有子评论 根据评论id进行查询 （parent_id）
         */
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,id);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVos = copyList(comments);
        return Result.success(commentVos);
    }

    private List<CommentVo> copyList(List<Comment> comments) {

        List<CommentVo> commentVos = new ArrayList<>();
        for (Comment comment : comments) {
            commentVos.add(copy(comment));
        }
        return commentVos;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        if (comment.getLevel().equals("1")){
            queryWrapper.eq(Comment::getParentId,comment.getId());
            List<Comment> childrens = commentMapper.selectList(queryWrapper);
            List<CommentVo> childrenVos = copyList(childrens);
            commentVo.setChildrens(childrenVos);
        }
        SysUser user = sysUserService.findUserByAuthorId(comment.getAuthorId());
        UserVo userVo = new UserVo();
        userVo = userToUserVo(user,userVo);
        commentVo.setUserVo(userVo);
        if (Integer.valueOf(comment.getLevel()) > 1){
            Long toUid = comment.getToUid();
            SysUser userByAuthorId = sysUserService.findUserByAuthorId(toUid);
            UserVo userVo1 = new UserVo();
            userVo1 = userToUserVo(userByAuthorId,userVo1);
            commentVo.setToUser(userVo1);
        }
        return commentVo;
    }
    private UserVo userToUserVo(SysUser user,UserVo userVo){
        userVo.setAccount(user.getAccount());
        userVo.setAvatar(user.getAvatar());
        userVo.setNickname(user.getNickname());
        return userVo;
    }

    @Override
    public Result addComment(CommentParams commentParams) {
        Comment comment = new Comment();
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        comment.setAuthorId(sysUser.getId());
        long parent = commentParams.getParent();
        if (parent == 0){
            comment.setLevel("1");
        }else {
            comment.setLevel("2");
        }
        comment.setArticleId(commentParams.getArticleId());
        comment.setToUid(commentParams.getToUserId());
        comment.setContent(commentParams.getContent());
        comment.setParentId(commentParams.getParent());
        comment.setCreateDate(System.currentTimeMillis());


        commentMapper.insert(comment);
        return Result.success(comment);
    }
}
