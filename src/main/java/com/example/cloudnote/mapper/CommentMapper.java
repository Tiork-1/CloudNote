package com.example.cloudnote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cloudnote.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
