package com.delete.blogg.vo.params;

import lombok.Data;

@Data
public class PageParams {
    private int page = 1;
    private int pageSize = 10;

    private  long categoryId;
    private long tagId;

    private int year;
    private int month;
}
