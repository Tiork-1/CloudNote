package com.example.cloudnote.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_comment")
public class Comment {
    private String id;
    private String userId;
    private String passageId;
    private String content;
    private Date createTime;
    private Date updateTime;
}
