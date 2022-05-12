package com.example.cloudnote.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user")
public class User {
    private String id;
    private String username;
    private String password;
    private String avatar;
    private Date createTime;
    private Date updateTime;
}
