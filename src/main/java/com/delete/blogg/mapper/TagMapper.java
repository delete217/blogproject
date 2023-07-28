package com.delete.blogg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.delete.blogg.entity.Tag;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询tag名称
     * @param articleId
     * @return
     */

    List<Tag> findTagsByArticleId(long articleId);

    List<Long> findHotTag(int limit);
}
