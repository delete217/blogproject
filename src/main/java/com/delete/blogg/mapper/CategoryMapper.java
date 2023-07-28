package com.delete.blogg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.delete.blogg.entity.Category;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
public interface CategoryMapper extends BaseMapper<Category> {


    Category findCategoryById(long categoryId);

}
