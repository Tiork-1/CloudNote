package com.example.cloudnote.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.cloudnote.mapper.UserMapper;
import com.example.cloudnote.pojo.User;
import com.example.cloudnote.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

//    is user
    public User isUser(String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        List<User> users = userMapper.selectList(queryWrapper);

        if (users.size()>0){
            return users.get(0);
        }
        return null;
    }

//    add
    public String addUser(User user){
        if (user.getPassword().length()==0 || user.getUsername().length()==0){
            return null;
        }
        user.setId(UUIDGenerator.getUUID());
        user.setAvatar("https://img1.baidu.com/it/u=639350735,1469121496&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=400");
        if (userMapper.insert(user)<=0){
            return null;
        }
        return user.getId();
    }

    public boolean updateAvatar(String userId,String newAvatar){
        User user = new User();
        user.setId(userId);
        user.setAvatar(newAvatar);
        userMapper.updateById(user);
        return true;
    }

    public User getUserById(String id){
        User user = userMapper.selectById(id);
        return user;
    }

    public boolean reSetAvatar(String userId,String url){
        User user = new User();
        user.setId(userId);
        user.setAvatar(url);
        userMapper.updateById(user);
        return true;
    }
}
