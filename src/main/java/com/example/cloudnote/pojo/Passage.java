package com.example.cloudnote.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_passage")
public class Passage {
    private String id;
    private String title;
    private String content;
    private Integer readers;
    private String userId;
    private Date createTime;
    private Date updateTime;
    private boolean isPublic;
}
