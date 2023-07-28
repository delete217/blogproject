package com.delete.blogg.vo.params;

import lombok.Data;

@Data
public class CommentParams {
    private long articleId;

    private String content;

    private long parent;

    private long toUserId;
}
