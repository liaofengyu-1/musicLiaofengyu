package com.lfy.excel.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    // id
    private int id;
    // 语言
    private String languages;
    // url
    private String url;
    // 歌名
    private String name;
}
