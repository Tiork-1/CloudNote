package com.example.cloudnote.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cloudnote.mapper.CommentMapper;
import com.example.cloudnote.pojo.Comment;
import com.example.cloudnote.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    public List<Comment> getComments(String passageId){
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("passage_id",passageId);
        List<Comment> comments = commentMapper.selectList(wrapper);
        return comments;
    }

    public boolean addComment(Comment comment){
        comment.setId(UUIDGenerator.getUUID());
        commentMapper.insert(comment);
        return true;
    }
}
