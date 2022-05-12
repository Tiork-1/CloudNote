package com.example.cloudnote.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.cloudnote.mapper.LikeMapper;
import com.example.cloudnote.mapper.PassageMapper;
import com.example.cloudnote.pojo.Like;
import com.example.cloudnote.pojo.Passage;
import com.example.cloudnote.util.RandomListUtil;
import com.example.cloudnote.util.StringUtil;
import com.example.cloudnote.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PassageService {

    @Autowired
    PassageMapper passageMapper;

    public String addPassage(Passage passage){
        if (StringUtil.check(passage.getUserId())&&StringUtil.check(passage.getTitle()) && StringUtil.check(passage.getContent())){
            String id = UUIDGenerator.getUUID();
            passage.setId(id);
            passage.setReaders(0);
            passageMapper.insert(passage);
            return id;
        }
        return null;
    }

    public boolean deletePassage(String id){
        passageMapper.deleteById(id);
        return true;
    }

    public List<Passage> getListById(String id){
        QueryWrapper<Passage> wrapper = new QueryWrapper();
        wrapper.eq("user_id",id);
        List<Passage> passages = passageMapper.selectList(wrapper);
        return passages;
    }

    public Passage getPassageById(String id){
        QueryWrapper<Passage> wrapper = new QueryWrapper();
        wrapper.eq("id",id);
        List<Passage> passages = passageMapper.selectList(wrapper);
        return passages.get(0);
    }

    public boolean ifPassageExist(String id){
        Passage passages = passageMapper.selectById(id);
        if (passages!=null){
            return true;
        }
        return false;
    }

    public boolean updatePassage(Passage passage){
        UpdateWrapper<Passage> wrapper = new UpdateWrapper<>();
        passageMapper.updateById(passage);
        return true;
    }

    @Autowired
    LikeMapper likeMapper;

    public List<Passage> getRandomPassage(String userId,boolean needstar,String search){
        List<Passage> passages = new ArrayList<>();
        if (needstar){
            QueryWrapper<Like> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",userId);
            List<Like> likes = likeMapper.selectList(wrapper);
            for(Like like:likes){
                passages.add(getPassageById(like.getPassageId()));
            }
        }else {
            passages = passageMapper.selectList(null);
        }

        List<Passage> passages1 = new ArrayList<>();
        for (int i = 0; i < passages.size(); i++) {
            String title = passages.get(i).getTitle();
            if (title.contains(search)){
                passages1.add(passages.get(i));
            }
        }

        RandomListUtil<Passage> passageRandomListUtil = new RandomListUtil<>(passages1);
        return passageRandomListUtil.getRandomList(8);
    }

    public boolean addRead(String passageId){
        Passage passage = passageMapper.selectById(passageId);
        Integer readers = passage.getReaders();
        readers += 1;
        passage.setReaders(readers);
        passageMapper.updateById(passage);
        return true;
    }
}
