package com.delete.blogg.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private UserVo userVo;

    private List<CommentVo> childrens;

    private String content;

    private Long createDate;

    private String level;

    private UserVo ToUser;

}
