package com.example.cloudnote.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_tag_use")
public class TagUse {
    private String passageId;
    private String tagId;
}
