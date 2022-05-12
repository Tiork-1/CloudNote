package com.example.cloudnote.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherController {
    @GetMapping("/tologin")
    public String toLogin(){
        return "login";
    }
    @GetMapping("/toeditor")
    public String toEditor(){
        return "editor";
    }
    @GetMapping("/toreader")
    public String toReader(){
        return "reader";
    }
    @GetMapping("/toexplorer")
    public String toExplorer(){
        return "explorer";
    }
    @GetMapping("/todreader")
    public String toDReader(){
        return "depart_reader";
    }
}
