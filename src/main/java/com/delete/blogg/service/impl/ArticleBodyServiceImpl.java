package com.delete.blogg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.delete.blogg.entity.ArticleBody;
import com.delete.blogg.mapper.ArticleBodyMapper;
import com.delete.blogg.service.IArticleBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
@Service
public class ArticleBodyServiceImpl extends ServiceImpl<ArticleBodyMapper, ArticleBody> implements IArticleBodyService {
    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Override
    public ArticleBody findBodyByBodyId(long bodyId) {
       return articleBodyMapper.findBodyByBodyId(bodyId);
    }
}
