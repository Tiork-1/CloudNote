package com.example.cloudnote.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExplorerReturn01 {
    private String passageId;
    private String username;
    private String readers;
    private String stars;
    private String avatar;
    private String content;
    private String title;
}
