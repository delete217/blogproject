package com.delete.blogg.controller;


import com.delete.blogg.common.aop.LogAnnotation;
import com.delete.blogg.common.cache.Cache;

import com.delete.blogg.service.IArticleBodyService;
import com.delete.blogg.service.IArticleService;
import com.delete.blogg.vo.Result;
import com.delete.blogg.vo.params.ArticleParams;
import com.delete.blogg.vo.params.PageParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
@RestController

@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IArticleBodyService articleBodyService;

    /**
     * 首页文章列表
     */
    //注释在哪个方法 就会对哪个方法生效
    @LogAnnotation(module = "文章", operator = "获取文章列表")
    @Cache(expire = 5 * 60 * 1000, name = "list_article")
    @ApiOperation("首页文章列表")
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams) {

//        int i = 10/0;
//        System.out.println(pageParams.getPage()+"<>"+pageParams.getPageSize());
        return articleService.listArticles(pageParams);

    }

//    @ApiOperation("查看文章内容")
//    @GetMapping("/details")
//    public Result showArticle(long articleId){
//        //用户点击内容时再进行查找
//        ArticleBody body = articleBodyService.findBodyByArticleId(articleId);
//        return Result.success(body);
//
//    }


    @ApiOperation("最热文章")
    @PostMapping("/hot")
    @Cache(expire = 5 * 60 * 1000, name = "hot_article")
    //自定义limit 前端有问题
    public Result hotArticle() {
        return articleService.findHotArticle(5);
    }


    @ApiOperation("最新文章")
    @PostMapping("/new")
    @Cache(expire = 5 * 60 * 1000, name = "new_article")
    public Result newArticle() {
        return articleService.finNewArticle(5);
    }


    @ApiOperation("文章按 年、月 归档")
    @PostMapping("/archives")
    @Cache(expire = 5 * 60 * 1000, name = "archive_article")
    public Result archive() {
        return articleService.archive();
    }


    @ApiOperation("根据id查询文章")
    @PostMapping("/view/{id}")
//    @Cache(expire = 5 * 60 * 1000, name = "view_article")
//    加上缓存会报错
    public Result findArticleById(@PathVariable long id) {
        return articleService.findArticleById(id);
    }


    @ApiOperation("发布文章")
    @PostMapping("/publish")
    @Cache(expire = 5 * 60 * 1000, name = "publish_article")
    public Result publish(@RequestBody ArticleParams articleParams) {
        return articleService.publish(articleParams);
    }


//    @ApiOperation("根据年、月查询文章")
//    @PostMapping("/showByDate/{year}/{month}")
//    @Cache(expire = 5 * 60 * 1000, name = "find_article_year_month")
//    public Result findArticleByDate(@PathVariable Integer year, @PathVariable Integer month) {
//        return articleService.findArticleByDate(year, month);
//    }

    @ApiOperation("根据文章title查询文章")
    @PostMapping("/search")
    public Result searchArticleByTitle(@RequestParam String title){
        return articleService.searchArticleByTitle(title);
    }

}
