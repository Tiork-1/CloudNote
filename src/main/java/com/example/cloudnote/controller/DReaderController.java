package com.example.cloudnote.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.cloudnote.mapper.UserMapper;
import com.example.cloudnote.pojo.*;
import com.example.cloudnote.pojo.vo.DReaderReturn01;
import com.example.cloudnote.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dreader")
public class DReaderController {

    @Autowired
    TagService tagService;
    @Autowired
    PassageService passageService;

    @RequestMapping("/")
    public String departReader(){
        return "depart_reader";
    }

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @RequestMapping("/{id}")
    public String departReaderWithId(@PathVariable("id") String id, Model model){
        String userId = (String) StpUtil.getLoginId();
        List<Tag> tags = tagService.getTagById(id);
        model.addAttribute("tags",tags);
        Passage passage = passageService.getPassageById(id);
        model.addAttribute("passage",passage);
        User user = userService.getUserById(passage.getUserId());
        model.addAttribute("user",user);
        Integer stars = likeService.getLikeNum(passage.getId());
        model.addAttribute("stars",stars.toString());
        if (likeService.isLike(userId,id)){
            model.addAttribute("isLike","yes");
        }else {
            model.addAttribute("isLike","no");
        }
        //评论
        List<Comment> comments = commentService.getComments(id);
        List<DReaderReturn01> dReaderReturn01s = new ArrayList<>();
        for(Comment comment:comments){
            DReaderReturn01 dReaderReturn01 = new DReaderReturn01();
            dReaderReturn01.setContent(comment.getContent());
            dReaderReturn01.setUsername(userService.getUserById(comment.getUserId()).getUsername());
            dReaderReturn01s.add(dReaderReturn01);
        }
        model.addAttribute("comments",dReaderReturn01s);

        return "depart_reader";
    }

    @RequestMapping("/like")
    @ResponseBody
    public String like(@RequestBody Map<String,Object> args){
        String passageId = (String) args.get("passageId");
        String isLike = (String) args.get("isLike");
        String userId = (String) StpUtil.getLoginId();

        if (isLike.equals("yes")){
            likeService.addLike(userId,passageId);
        }else {
            likeService.deleteLike(userId,passageId);
        }
        return "";
    }

    @ResponseBody
    @RequestMapping("/addread")
    public String addRead(@RequestBody Map<String,Object> args){
        String passageId = (String) args.get("passageId");
        passageService.addRead(passageId);
        return "";
    }

    @RequestMapping("/addcomment")
    @ResponseBody
    public String addComment(@RequestBody Map<String,Object> args){
        String content = (String) args.get("content");
        String userId = (String) StpUtil.getLoginId();
        String passageId = (String) args.get("passageId");
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPassageId(passageId);
        comment.setUserId(userId);
        commentService.addComment(comment);
        return "";
    }
}
