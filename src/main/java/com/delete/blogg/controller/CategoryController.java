package com.delete.blogg.controller;


import com.delete.blogg.service.ICategoryService;
import com.delete.blogg.vo.CategoryVo;
import com.delete.blogg.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public Result findAllCategory(){
        List<CategoryVo> categoryVos = categoryService.findAllCategory();
        return Result.success(categoryVos);
    }

    @GetMapping("/details")
    public Result categoryDetails(){
        return categoryService.finAllDetails();
    }

    @GetMapping("/details/{id}")
    public Result categoryDetailById(@PathVariable("id") long id){
        return categoryService.categoryDetailById(id);
    }
}
