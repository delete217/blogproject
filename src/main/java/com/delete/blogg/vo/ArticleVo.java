package com.delete.blogg.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private long id;

    private String title;

    private String summary;

    private int commentCounts;

    private int viewCounts;

    private int weight;

    private String author;

    private String body;

    private String createDate;

    private BodyVo bodyVo;

    private CategoryVo categoryVo;

    private List<TagVo> tags;
}
