package com.example.cloudnote.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.cloudnote.mapper.TagUseMapper;
import com.example.cloudnote.pojo.Tag;
import com.example.cloudnote.pojo.TagUse;
import com.example.cloudnote.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagUseService {

    @Autowired
    TagUseMapper tagUseMapper;

    public boolean addTagUse(String passageId,String tagId){
        if (StringUtil.check(passageId) && StringUtil.check(tagId)){
            if (!ifTagUseExist(passageId,tagId)){
                tagUseMapper.insert(new TagUse(passageId,tagId));
                return true;
            }
            return true;
        }
        return false;
    }

    public boolean ifTagUseExist(String passgeId,String tagId){
        QueryWrapper<TagUse> tagUseQueryWrapper = new QueryWrapper<>();
        tagUseQueryWrapper.eq("passage_id",passgeId)
                .eq("tag_id",tagId);
        List<TagUse> tagUses = tagUseMapper.selectList(tagUseQueryWrapper);
        if (tagUses.size()>0){
            return false;
        }
        return false;
    }
//    删除某篇文章的所有标签
    public boolean deletePassageTagUse(String passageId){
        QueryWrapper<TagUse> wrapper = new QueryWrapper<>();
        wrapper.eq("passage_id",passageId);
        tagUseMapper.delete(wrapper);
        return true;
    }

    public List<TagUse> getTagsById(String id){
        QueryWrapper<TagUse> wrapper = new QueryWrapper<>();
        wrapper.eq("passage_id",id);
        List<TagUse> tags = tagUseMapper.selectList(wrapper);
        return tags;
    }

}
