package com.delete.blogg.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveVo {

    private int year;
    private int month;
    private int count;

}
