package com.example.cloudnote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cloudnote.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
}
