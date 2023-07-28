package com.delete.blogg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.delete.blogg.entity.Category;
import com.delete.blogg.vo.CategoryVo;
import com.delete.blogg.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
public interface ICategoryService extends IService<Category> {

    Category findCategoryById(long categoryId);

    List<CategoryVo> findAllCategory();

    Result finAllDetails();

    Result categoryDetailById(long id);
}
