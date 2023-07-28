package com.delete.blogg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.delete.blogg.entity.Article;
import com.delete.blogg.vo.Result;
import com.delete.blogg.vo.params.ArticleParams;
import com.delete.blogg.vo.params.PageParams;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
public interface IArticleService extends IService<Article> {

    Result listArticles(PageParams pageParams);

    Result findHotArticle(int limit);

    Result finNewArticle(int limit);

    Result archive();

    Result findArticleById(long ArticleId);

    Result publish(ArticleParams articleParams);

    Result findArticleByDate(int year,int month);

    Result searchArticleByTitle(String title);
}
