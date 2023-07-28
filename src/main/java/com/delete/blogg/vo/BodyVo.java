package com.delete.blogg.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BodyVo {
    private String content;
    private String contentHtml;
}
