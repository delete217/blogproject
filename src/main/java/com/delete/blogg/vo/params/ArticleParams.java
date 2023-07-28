package com.delete.blogg.vo.params;

import com.delete.blogg.vo.CategoryVo;
import com.delete.blogg.vo.TagVo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParams {
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private List<TagVo> tags;

    private String summary;

    private String title;

}
