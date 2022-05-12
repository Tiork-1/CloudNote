package com.example.cloudnote.controller;


import com.alibaba.fastjson.JSON;
import com.example.cloudnote.pojo.Tag;
import com.example.cloudnote.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping("/gettags")
    @ResponseBody
    public String getTags(){
        List<Tag> tags = tagService.getTags();
        return JSON.toJSONString(tags);
    }

}
