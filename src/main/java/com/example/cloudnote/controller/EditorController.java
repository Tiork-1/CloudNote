package com.example.cloudnote.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.example.cloudnote.pojo.*;
import com.example.cloudnote.service.PassageService;
import com.example.cloudnote.service.TagService;
import com.example.cloudnote.service.TagUseService;
import com.example.cloudnote.service.UserService;
import com.example.cloudnote.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/editor")
public class EditorController {

    @Autowired
    PassageService passageService;
    @Autowired
    TagService tagService;

    @GetMapping("/")
    public String editor(Model model){
        String id = (String) StpUtil.getLoginId();
        List<Passage> list = passageService.getListById(id);
        model.addAttribute("passages",list);
        Passage emptyPassage = new Passage();
        emptyPassage.setTitle("");
        emptyPassage.setContent("");
        emptyPassage.setId("");
        List<TagUse> list1 = new ArrayList<>();
        model.addAttribute("passage",emptyPassage);
        model.addAttribute("passageTags",list1);
        List<Tag> tagList = tagService.getTags();
        model.addAttribute("tags",tagList);

        User user = userService.getUserById((String) StpUtil.getLoginId());
        model.addAttribute("user",user);
        return "editor";
    }

//    @GetMapping("/getList")
//    @ResponseBody
//    public String getListById(){
//        String id = (String) StpUtil.getLoginId();
//        List<Passage> passageList = passageService.getListById(id);
//        return JSON.toJSONString(passageList);
//    }
//
//
//    @PostMapping("/getpassagebyid")
//    @ResponseBody
//    public String getPassageById(@RequestBody Map<String,Object> args){
//        String id = (String) args.get("id");
//        Passage passage = passageService.getPassageById(id);
//        return JSON.toJSONString(passage);
//    }

    @Autowired
    TagUseService tagUseService;
    @Autowired
    UserService userService;

    @RequestMapping("/{id}")
    public String editorWithId(@PathVariable("id") String id,Model model){
        String userId = (String) StpUtil.getLoginId();
        List<Passage> list = passageService.getListById(userId);
        model.addAttribute("passages",list);

        Passage passage = passageService.getPassageById(id);
        List<TagUse> tagList = tagUseService.getTagsById(id);
        System.out.println(tagList);
        model.addAttribute("passage",passage);
        model.addAttribute("passageTags",tagList);

        List<Tag> tags = tagService.getTags();
        model.addAttribute("tags",tags);

        User user = userService.getUserById((String) StpUtil.getLoginId());
        model.addAttribute("user",user);
        return "editor";
    }

    @RequestMapping("/submit")
    @ResponseBody
    public Result submit(@RequestBody Map<String,Object> args, Model model){
        String content = (String) args.get("content");
        String id = (String) args.get("id");
        String title = (String) args.get("title");
        List<String> tags = (List<String>) JSON.parse((String) args.get("tags"));
        if (!StringUtil.check(title)){
            return new Result(false,"请输入标题");
        }
        if (!StringUtil.check(content)){
            return new Result(false,"文本内容为空");
        }
//        判断是更新还是新建
        if (StringUtil.check(id)){
            Passage passage = new Passage();
            passage.setId(id);
            passage.setTitle(title);
            passage.setContent(content);
            passageService.updatePassage(passage);
        }else {
            Passage passage = new Passage();
            passage.setTitle(title);
            passage.setContent(content);
            passage.setReaders(0);
            passage.setUserId((String) StpUtil.getLoginId());
            id = passageService.addPassage(passage);
        }

//        删除该文章标签
        tagUseService.deletePassageTagUse(id);
        for(String tagId : tags){
            tagUseService.addTagUse(id,tagId);
            System.out.println(id+"   "+tagId);
        }

        return new Result(true,"publish!!");
    }

    @RequestMapping("/deletePassage")
    @ResponseBody
    public Result deletePassage(@RequestBody Map<String,Object> args, Model model){
        String passageId = (String) args.get("id");
        if (!StringUtil.check(passageId)){
            return new Result(false,"id为空");
        }
//        先删除所有依附的标签
        tagUseService.deletePassageTagUse(passageId);
//        删除这个文章
        passageService.deletePassage(passageId);
        return new Result(true,"删除成功");
    }
    @RequestMapping("setavatar")
    @ResponseBody
    public String setAvatar(@RequestBody Map<String,Object> args){
        String url = (String) args.get("url");
        userService.reSetAvatar((String) StpUtil.getLoginId(),url);
        return "";
    }
}
