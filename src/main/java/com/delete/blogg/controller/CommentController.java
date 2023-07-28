package com.delete.blogg.controller;


import com.delete.blogg.service.ICommentService;
import com.delete.blogg.vo.Result;
import com.delete.blogg.vo.params.CommentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @GetMapping("/article/{id}")
    public Result comments(@PathVariable("id") long id){
        return commentService.commentsByArticleId(id);
    }

    @PostMapping("/create/change")
    public Result comment(@RequestBody CommentParams commentParams){
        return commentService.addComment(commentParams);
    }
}
