package com.delete.blogg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.delete.blogg.entity.Category;
import com.delete.blogg.mapper.CategoryMapper;
import com.delete.blogg.service.ICategoryService;
import com.delete.blogg.vo.CategoryVo;
import com.delete.blogg.vo.Result;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Category findCategoryById(long categoryId) {
        return categoryMapper.findCategoryById(categoryId);
    }

    @Override
    public List<CategoryVo> findAllCategory() {
        List<Category> categories = categoryMapper.selectList(null);
        List<CategoryVo> categoryVos = new ArrayList<>();
        for (Category category : categories) {
            categoryVos.add(copy(category));
        }
        return categoryVos;
    }

    public CategoryVo copy(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
//        categoryVo.setCategoryName(category.getCategoryName());
//        categoryVo.setId(category.getId());
//        categoryVo.setAvatar(category.getAvatar());
        return categoryVo;

    }

    @Override
    public Result finAllDetails() {
        LambdaQueryWrapper<Object> queryWrapper = new LambdaQueryWrapper<>();
        List<Category> categories = categoryMapper.selectList(null);

        return Result.success(categories);
    }

    @Override
    public Result categoryDetailById(long id) {
        Category category = categoryMapper.selectById(id);
        return Result.success(copy(category));
    }
}
