package com.example.cloudnote.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.example.cloudnote.pojo.Passage;
import com.example.cloudnote.pojo.Tag;
import com.example.cloudnote.pojo.TagUse;
import com.example.cloudnote.pojo.User;
import com.example.cloudnote.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reader")
public class ReaderController {
    @Autowired
    PassageService passageService;
    @Autowired
    TagUseService tagUseService;
    @Autowired
    TagService tagService;
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String toReader(Model model){
        String id = (String) StpUtil.getLoginId();
        List<Passage> list = passageService.getListById(id);
        model.addAttribute("passages",list);
        Passage emptyPassage = new Passage();
        emptyPassage.setTitle("");
        emptyPassage.setContent("### 点击笔记列表查看你的笔记吧");
        emptyPassage.setId("");
        emptyPassage.setReaders(0);
        List<TagUse> list1 = new ArrayList<>();
        model.addAttribute("passage",emptyPassage);
        model.addAttribute("passageTags",list1);
        List<Tag> tagList = tagService.getTags();
        model.addAttribute("tags",tagList);
        model.addAttribute("stars","0");


        User user = userService.getUserById((String) StpUtil.getLoginId());
        model.addAttribute("user",user);
        return "reader";
    }

    @Autowired
    LikeService likeService;


    @RequestMapping("/{id}")
    public String readWithId(@PathVariable("id") String id, Model model){
        String userId = (String) StpUtil.getLoginId();
        List<Passage> list = passageService.getListById(userId);
        model.addAttribute("passages",list);

        Passage passage = passageService.getPassageById(id);
        List<TagUse> tagList = tagUseService.getTagsById(id);
        System.out.println(tagList);
        model.addAttribute("passage",passage);
        model.addAttribute("stars",likeService.getLikeNum(id).toString());
        model.addAttribute("passageTags",tagList);

        List<Tag> tags = tagService.getTags();
        model.addAttribute("tags",tags);

        User user = userService.getUserById((String) StpUtil.getLoginId());
        model.addAttribute("user",user);
        return "reader";
    }






}
