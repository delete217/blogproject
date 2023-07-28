package com.delete.blogg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.delete.blogg.entity.Tag;
import com.delete.blogg.vo.Result;
import com.delete.blogg.vo.TagVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
public interface ITagService extends IService<Tag> {
    List<TagVo> findTagsByArticleId(Long id);

    Result findHotTag(int limit);

    List<TagVo> findAllTags();

    Result findAllDetails();

    Result findDetailById(Long id);
}
