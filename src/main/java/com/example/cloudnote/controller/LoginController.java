package com.example.cloudnote.controller;

import ch.qos.logback.classic.Logger;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.example.cloudnote.pojo.Result;
import com.example.cloudnote.pojo.User;
import com.example.cloudnote.service.UserService;
import com.example.cloudnote.util.IVerifyCodeGen;
import com.example.cloudnote.util.SimpleCharVerifyCodeGenImpl;
import com.example.cloudnote.util.StringUtil;
import com.example.cloudnote.util.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private static Map<String,String> codeMap = new HashMap<>();

    @Autowired
    UserService userService;

    @GetMapping({"/","index"})
    public String index(){
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody Map<String,Object> args){
        String username = (String) args.get("username");
        if (!StringUtil.check(username)){
            return JSON.toJSONString(new Result(false,"请输入用户名"));
        }
        String password = (String) args.get("password");
        if (!StringUtil.check(password)){
            return JSON.toJSONString(new Result(false,"请输入密码"));
        }
        String verifycode = (String) args.get("verifycode");
        String selfCode = (String) args.get("selfCode");
        String code = codeMap.get(selfCode);
//        用户输入的验证码和实际验证码转化一下
        verifycode = verifycode.toUpperCase();
        code = code.toUpperCase();
        System.out.println(username+" "+password+" "+verifycode+" "+code+" "+selfCode);

        if (!verifycode.equals(code)){
            return JSON.toJSONString(new Result(false,"验证码错误")) ;
        }

        User user =  userService.isUser(username);
        if (user==null){
            user = new User();
            user.setUsername(username);
            user.setPassword(password);
            String id = userService.addUser(user);
            if(id==null){
                return JSON.toJSONString(new Result(false,"未知错误")) ;
            }
            StpUtil.login(id);
            return JSON.toJSONString(new Result(true,"注册成功"));
        }else if(!user.getPassword().equals(password)){
            return JSON.toJSONString(new Result(false,"密码错误"));
        }
        StpUtil.login(user.getId());
        return JSON.toJSONString(new Result(true,"登录成功"));
    }

    @GetMapping("logout")
    public String logout(){
        StpUtil.logout();
        return "/login";
    }


    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response, HttpSession session, String selfCode) {
        System.out.println("get code");
        IVerifyCodeGen iVerifyCodeGen = new SimpleCharVerifyCodeGenImpl();
        try {
            //设置长宽
            VerifyCode verifyCode = iVerifyCodeGen.generate(80, 28);
            String code = verifyCode.getCode();
            codeMap.put(selfCode,code);

            //设置响应头
            response.setHeader("Pragma", "no-cache");
            //设置响应头
            response.setHeader("Cache-Control", "no-cache");
            //在代理服务器端防止缓冲
            response.setDateHeader("Expires", 0);
            //设置响应内容类型
            response.setContentType("image/jpeg");
            response.getOutputStream().write(verifyCode.getImgBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
