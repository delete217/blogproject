package com.delete.blogg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.delete.blogg.entity.ArticleTag;
import com.delete.blogg.mapper.ArticleTagMapper;
import com.delete.blogg.service.IArticleTagService;
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
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements IArticleTagService {

}
