package com.delete.blogg.controller;


import com.delete.blogg.entity.Tag;
import com.delete.blogg.service.ITagService;
import com.delete.blogg.vo.Result;
import com.delete.blogg.vo.TagVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
@RestController
@RequestMapping("/tags")
public class TagController {


    @Autowired
    private ITagService tagService;

    @ApiOperation("查找最热标签")
    @GetMapping("/hotTags")
    public Result hotTags(){
       return tagService.findHotTag(4);
    }

    @GetMapping
    public Result findAllTags(){
        List<TagVo> tagVos = tagService.findAllTags();
        return Result.success(tagVos);
    }

    @GetMapping("/details")
    public Result findAllDetails(){
        return tagService.findAllDetails();
    }
    @GetMapping("/details/{id}")
    public Result findAllDetails(@PathVariable("id") Long id){
        return tagService.findDetailById(id);

    }


}
