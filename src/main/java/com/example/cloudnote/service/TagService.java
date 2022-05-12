package com.example.cloudnote.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cloudnote.mapper.TagMapper;
import com.example.cloudnote.pojo.Tag;
import com.example.cloudnote.pojo.TagUse;
import com.example.cloudnote.util.StringUtil;
import com.example.cloudnote.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    TagMapper tagMapper;

    public List<Tag> getTags(){
        List<Tag> tagList = tagMapper.selectList(null);
        return tagList;
    }


    public String addTag(String tagName){
        Tag tag = new Tag();
        tag.setId(UUIDGenerator.getUUID());
        tag.setName(tagName);
        tagMapper.insert(tag);
        return tag.getId();
    }

    @Autowired
    TagUseService tagUseService;

    public List<Tag> getTagById(String passageId){
        List<Tag> tags = new ArrayList<>();
        List<TagUse> tagUses = tagUseService.getTagsById(passageId);

        for(TagUse tagUse:tagUses){
            tags.add(tagMapper.selectById(tagUse.getTagId()));
        }
        return tags;
    }
}
