package com.delete.blogg.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.delete.blogg.entity.Article;
import com.delete.blogg.mapper.ArticleMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    //期望此操作在线程池执行 不影响原有主线程
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        Integer viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        articleUpdate.setCategoryId(article.getCategoryId());
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,article.getId());
        //为了多线程环境下线程安全，需以下操作
        updateWrapper.eq(Article::getViewCounts,viewCounts);
        articleMapper.update(articleUpdate,updateWrapper);
    }
}
