package com.delete.blogg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.delete.blogg.entity.ArticleBody;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
public interface ArticleBodyMapper extends BaseMapper<ArticleBody> {

    ArticleBody findBodyByBodyId(long articleId);
}
