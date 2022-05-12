package com.example.cloudnote.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.cloudnote.mapper.UserMapper;
import com.example.cloudnote.pojo.Passage;
import com.example.cloudnote.pojo.User;
import com.example.cloudnote.pojo.vo.ExplorerReturn01;
import com.example.cloudnote.service.LikeService;
import com.example.cloudnote.service.PassageService;
import com.example.cloudnote.service.UserService;
import com.example.cloudnote.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/explorer")
public class ExplorerController {

    @Autowired
    PassageService passageService;

    @RequestMapping("/")
    public String exploer(Model model){
        List<Passage> passages = passageService.getRandomPassage("",false,"");
        List<ExplorerReturn01> explorerReturn01s = new ArrayList<>();
        for (int i = 0; i < passages.size(); i++) {
            Passage passage = passages.get(i);
            User user = userService.getUserById(passage.getUserId());
            Integer likeNum = likeService.getLikeNum(passage.getId());

            ExplorerReturn01 temp = new ExplorerReturn01();
            temp.setAvatar(user.getAvatar());
            temp.setContent(passage.getContent());
            temp.setReaders(passage.getReaders().toString());
            temp.setStars(likeNum.toString());
            temp.setTitle(passage.getTitle());
            temp.setUsername(user.getUsername());
            temp.setPassageId(passage.getId());

            explorerReturn01s.add(temp);
        }

        model.addAttribute("passages",explorerReturn01s);

        User user = userService.getUserById((String) StpUtil.getLoginId());
        model.addAttribute("user",user);
        return "explorer";
    }

    @Autowired
    UserService userService;
    @Autowired
    LikeService likeService;

    @GetMapping("/search")
    public String search(String isStar,String search,Model model){
//        String isStar = (String) args.get("isStar");
//        boolean star = false;
//        if (StringUtil.check(isStar) && isStar.equals("yes")){
//            star = true;
//        }else {
//            star = false;
//        }
//        String search = (String) args.get("isStar");
//        if (!StringUtil.check(search)){
//            search = "";
//        }
        boolean star = false;
        if (StringUtil.check(isStar) && isStar.equals("yes")){
            star = true;
        }
        if (!StringUtil.check(search)){
            search = "";
        }
//        System.out.println(search);
        List<Passage> passages = passageService.getRandomPassage((String) StpUtil.getLoginId(),star,search);
        List<ExplorerReturn01> explorerReturn01s = new ArrayList<>();
        for (int i = 0; i < passages.size(); i++) {
            Passage passage = passages.get(i);
            User user = userService.getUserById(passage.getUserId());
            Integer likeNum = likeService.getLikeNum(passage.getId());

            ExplorerReturn01 temp = new ExplorerReturn01();
            temp.setAvatar(user.getAvatar());
            temp.setContent(passage.getContent());
            temp.setReaders(passage.getReaders().toString());
            temp.setStars(likeNum.toString());
            temp.setTitle(passage.getTitle());
            temp.setUsername(user.getUsername());
            temp.setPassageId(passage.getId());

            explorerReturn01s.add(temp);
        }

//        System.out.println(explorerReturn01s);

        model.addAttribute("passages",explorerReturn01s);

        User user = userService.getUserById((String) StpUtil.getLoginId());
        model.addAttribute("user",user);
        return "explorer";
    }
}
