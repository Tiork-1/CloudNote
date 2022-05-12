package com.example.cloudnote.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cloudnote.mapper.LikeMapper;
import com.example.cloudnote.pojo.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    LikeMapper likeMapper;

    public Integer getLikeNum(String passageId){
        QueryWrapper<Like> wrapper = new QueryWrapper<>();
        wrapper.eq("passage_id",passageId);
        List<Like> likes = likeMapper.selectList(wrapper);
        return likes.size();
    }

    public boolean addLike(String userId,String passageId){
        int res = likeMapper.insert(new Like(userId,passageId));
        if (res>0){
            return true;
        }
        return false;
    }

    public boolean deleteLike(String userId,String passageId){
        QueryWrapper<Like> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId).eq("passage_id",passageId);
        likeMapper.delete(wrapper);
        return true;
    }

    public boolean isLike(String userId,String passageId){
        QueryWrapper<Like> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId).eq("passage_id",passageId);
        List<Like> likes = likeMapper.selectList(wrapper);
        if (likes.size()>0){
            return true;
        }
        return false;
    }
}
