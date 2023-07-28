package com.delete.blogg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.delete.blogg.entity.*;
import com.delete.blogg.mapper.ArticleBodyMapper;
import com.delete.blogg.mapper.ArticleMapper;
import com.delete.blogg.mapper.ArticleTagMapper;
import com.delete.blogg.service.*;
import com.delete.blogg.utils.UserThreadLocal;
import com.delete.blogg.vo.*;
import com.delete.blogg.vo.params.ArticleParams;
import com.delete.blogg.vo.params.PageParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ITagService tagService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IArticleBodyService articleBodyService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ThreadService threadService;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;


    @Override
    public Result listArticles(PageParams pageParams) {
        /**
         * 分页查询article表
         */
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        //用于 按分类显示文章接口
        if (pageParams.getCategoryId() != 0) {
            queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
        }

        //用于 按标签显示文章接口
        if (pageParams.getTagId() != 0) {
            LambdaQueryWrapper<ArticleTag> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(ArticleTag::getTagId, pageParams.getTagId());
            List<ArticleTag> articleTags = articleTagMapper.selectList(queryWrapper1);
            List<Long> articleIds = new ArrayList<>();
            for (ArticleTag articleTag : articleTags) {
                articleIds.add(articleTag.getArticleId());
            }
            if (articleIds.size() > 0) {
                queryWrapper.in(Article::getId, articleIds);
            }
        }

        //用于按日期给文章归档
        if (pageParams.getYear() != 0 && pageParams.getMonth() !=0){
            List<Article> articles = articleMapper.findArticleByDate(pageParams.getYear(),pageParams.getMonth());
            List<ArticleVo> articleVoList = copyList(articles, true, true);
            return Result.success(articleVoList);
        }


        //按是否置顶进行排序
        //按创建时间倒序排列
        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleVo> articleVoList = copyList(records, true, true);
        return Result.success(articleVoList);
    }


//    @Override
//    public Result listArticles(PageParams pageParams) {
//        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
////        System.out.println("=======");
//        IPage<Article> articleIPage = articleMapper.listArticle(
//                page,
//                pageParams.getCategoryId(),
//                pageParams.getTagId(),
//                pageParams.getYear(),
//                pageParams.getMonth());
//        List<Article> records = articleIPage.getRecords();
//        return Result.success(copyList(records,true,true));
//
//    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, isBody, isCategory));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor) {

        ArticleVo articleVo = new ArticleVo();

        BeanUtils.copyProperties(article, articleVo);

        //日期格式转换
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(new Date(article.getCreateDate()));
        articleVo.setCreateDate(dateString);

        Long articleId = article.getId();
        if (isTag) {
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            SysUser user = sysUserService.findUserByAuthorId(authorId);
            articleVo.setAuthor(user.getNickname());
        }

        return articleVo;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {

        ArticleVo articleVo = new ArticleVo();

        BeanUtils.copyProperties(article, articleVo);

        //日期格式转换
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(new Date(article.getCreateDate()));
        articleVo.setCreateDate(dateString);

        Long articleId = article.getId();
        if (isTag) {
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            SysUser user = sysUserService.findUserByAuthorId(authorId);
            articleVo.setAuthor(user.getNickname());
        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            ArticleBody body = articleBodyService.findBodyByBodyId(bodyId);
            BodyVo bodyVo = new BodyVo();
            bodyVo.setContent(body.getContent());
            articleVo.setBodyVo(bodyVo);
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            Category category = categoryService.findCategoryById(categoryId);

            CategoryVo categoryVo = new CategoryVo();
            categoryVo.setId(categoryId);
            categoryVo.setAvatar(category.getAvatar());
            categoryVo.setCategoryName(category.getCategoryName());
            articleVo.setCategoryVo(categoryVo);
        }
        return articleVo;
    }

    @Override
    public Result findHotArticle(int limit) {

        List<Article> articles = articleMapper.findHotArticle(limit);
        List<ArticleVo> articleVoList = new ArrayList<>();
        articleVoList = copyList(articles, false, false, false, false);
        return Result.success(articleVoList);

//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.orderByDesc(Article::getViewCounts);
//        queryWrapper.select(Article::getId,Article::getTitle);
        //"limit " 一定要加空格！！
//        queryWrapper.last("limit " + limit);
//        List<Article> articles = articleMapper.selectList(queryWrapper);
//        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result finNewArticle(int limit) {

        List<Article> articles = articleMapper.finNewArticle(limit);
        List<ArticleVo> articleVoList = new ArrayList<>();
        articleVoList = copyList(articles, false, false, false, false);
        return Result.success(articleVoList);

//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.orderByDesc(Article::getCreateDate);
//        queryWrapper.last("limit "+ limit);
//        List<Article> articles = articleMapper.selectList(queryWrapper);
//        List<ArticleVo> articleVoList = new ArrayList<>();
//        articleVoList = copyList(articles, false, false);
//        return Result.success(articleVoList);
    }

    @Override
    public Result archive() {
        List<ArchiveVo> archiveVos = articleMapper.archive();
        return Result.success(archiveVos);
    }

    @Override
    public Result findArticleById(long ArticleId) {

        Article article = articleMapper.selectById(ArticleId);
        ArticleVo articleVo = copy(article, true, true, true, true);
        //文章阅读数 + 1   到线程池中运行，避免影响主线程
        threadService.updateArticleViewCount(articleMapper, article);
        return Result.success(articleVo);
    }

    @Override
    public Result publish(ArticleParams articleParams) {
        //前提是 此接口要加入到登录拦截
        SysUser sysUser = UserThreadLocal.get();
        Article article = new Article();
        //插入后生成一个文章id

        article.setViewCounts(0);
        article.setCategoryId(articleParams.getCategory().getId());
        article.setWeight(0);
        article.setTitle(articleParams.getTitle());
        article.setCommentCounts(0);
        article.setSummary(articleParams.getSummary());
        article.setCreateDate(System.currentTimeMillis());
        articleMapper.insert(article);

        article.setAuthorId(sysUser.getId());
        List<TagVo> tags = articleParams.getTags();

        //tag
        if (tags != null) {
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);

                articleTag.setTagId(tag.getId());
                articleTagMapper.insert(articleTag);
                System.out.println(articleId + "===" + articleParams.getId());
            }
        }

        //body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParams.getBody().getContent());
        articleBody.setContentHtml(articleParams.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        Map<String, String> map = new HashMap<>();
        map.put("id", article.getId().toString());
        return Result.success(map);
    }

    @Override
    public Result findArticleByDate(int year,int month) {
        List<Article> articles = articleMapper.findArticleByDate(year,month);
        List<ArticleVo> articleVoList = copyList(articles, true, true);
        return Result.success(articleVoList);
    }

    @Override
    public Result searchArticleByTitle(String title) {

        List<Article> articles = articleMapper.searchArticleByTitle(title);
        if (articles.size()>0){
            List<ArticleVo> articleVoList = copyList(articles, true, true);
            return Result.success(articleVoList);
        }
        return Result.success("抱歉，您查询的文章没找到~");
    }
}
