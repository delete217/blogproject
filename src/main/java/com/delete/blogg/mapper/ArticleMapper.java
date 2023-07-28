package com.delete.blogg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.delete.blogg.entity.Article;
import com.delete.blogg.vo.ArchiveVo;
import com.delete.blogg.vo.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> findHotArticle(int limit);

    List<Article> finNewArticle(int limit);

    List<ArchiveVo> archive();

   // IPage<Article> listArticle(Page<Article> page, long categoryId,long tagId, String year, String month);

    List<Article> findArticleByDate(int year,int month);

    List<Article> searchArticleByTitle(String title);
}
