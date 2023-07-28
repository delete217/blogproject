package com.delete.blogg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.delete.blogg.entity.Tag;
import com.delete.blogg.mapper.TagMapper;
import com.delete.blogg.service.ITagService;
import com.delete.blogg.vo.Result;
import com.delete.blogg.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    @Autowired
    private TagMapper tagMapper;

    public List<TagVo> copyList(List<Tag> tags){
        List<TagVo> tagVo = new ArrayList<>();
        for (Tag tag : tags) {
            tagVo.add(copy(tag));
        }

        return tagVo;
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        List<TagVo> tagVos = new ArrayList<>();
        for (Tag tag : tags) {
            tag.getTagName();
            tagVos.add(copy(tag));
        }
        return tagVos;
    }

    @Override
    public Result findHotTag(int limit) {
        List<Long> hotTagIds = tagMapper.findHotTag(limit);
//        List<String> hotTagNames = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        List<TagVo> tagVos = new ArrayList<>();
        System.out.println(hotTagIds);
        for (Long hotTagId : hotTagIds) {
            Tag tag = tagMapper.selectById(hotTagId);
            tags.add(tag);
        }
        tagVos = copyList(tags);
        return Result.success(tagVos);
    }
    @Override
    public List<TagVo> findAllTags() {
        List<Tag> tags = tagMapper.selectList(null);
        List<TagVo> tagVos = new ArrayList<>();
        for (Tag tag : tags) {
            tagVos.add(copy(tag));
        }
        return tagVos;
    }

    @Override
    public Result findAllDetails() {
        List<Tag> tags = tagMapper.selectList(null);
        return Result.success(tags);
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(tag);
    }
}
