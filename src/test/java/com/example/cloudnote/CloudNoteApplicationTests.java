package com.example.cloudnote;

import com.example.cloudnote.pojo.Passage;
import com.example.cloudnote.pojo.User;
import com.example.cloudnote.service.PassageService;
import com.example.cloudnote.service.TagService;
import com.example.cloudnote.service.TagUseService;
import com.example.cloudnote.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CloudNoteApplicationTests {

    @Autowired
    UserService userService;
    @Autowired
    PassageService passageService;
    @Test
    void contextLoads() {
        User user = new User();
        user.setUsername("qiuxinhan");
        user.setPassword("123456");
        System.out.println(userService.addUser(user));
    }

    @Test
    void test01(){
        Passage passage = new Passage();
        passage.setContent("");
        passage.setTitle("逆元");
        passage.setUserId("db38a3cd1a794039933eb3c7971e");
        System.out.println(passageService.addPassage(passage));
    }

    @Autowired
    TagService tagService;

    @Test
    public void test02(){
        System.out.println(tagService.addTag("数论"));
        System.out.println(tagService.addTag("数据结构"));
        System.out.println(tagService.addTag("字典"));
        System.out.println(tagService.addTag("二叉树"));
        System.out.println(tagService.addTag("动态规划"));
        System.out.println(tagService.addTag("计算机组成原理"));
        System.out.println(tagService.addTag("新闻"));
        System.out.println(tagService.addTag("音乐"));
        System.out.println(tagService.addTag("游戏"));
        System.out.println(tagService.addTag("LOL"));
    }

    @Test
    public void test03(){
        List<Passage> list = passageService.getListById("db38a3cd1a794039933eb3c7971e");
        System.out.println(list);
    }

    @Autowired
    TagUseService tagUseService;
    @Test
    public void test04(){
//        5a9282a930c54d99b7bebbee17a6
//        7dd9bb1b17c349148fc305478ca5
//        e98bb446497b4e0aa78008fa95d1

//        ccf7d6ac0d264629b891af69af91
//        db38a3cd1a794039933eb3c7971e
//        828c6fa09d50448cad334cd03a89
        tagUseService.addTagUse("ccf7d6ac0d264629b891af69af91",
                "5a9282a930c54d99b7bebbee17a6");
        tagUseService.addTagUse("ccf7d6ac0d264629b891af69af91",
                "7dd9bb1b17c349148fc305478ca5");
        tagUseService.addTagUse("ccf7d6ac0d264629b891af69af91",
                "e98bb446497b4e0aa78008fa95d1");
        tagUseService.addTagUse("db38a3cd1a794039933eb3c7971e",
                "5a9282a930c54d99b7bebbee17a6");
        tagUseService.addTagUse("db38a3cd1a794039933eb3c7971e",
                "e98bb446497b4e0aa78008fa95d1");
        tagUseService.addTagUse("828c6fa09d50448cad334cd03a89",
                "7dd9bb1b17c349148fc305478ca5");
        tagUseService.addTagUse("828c6fa09d50448cad334cd03a89",
                "e98bb446497b4e0aa78008fa95d1");

    }

    @Test
    void Test05(){
        System.out.println(tagUseService.ifTagUseExist("b75d17ef6a5f4dbc88d55fa861ef","5a9282a930c54d99b7bebbee17a6"));
    }

    @Test
    void test06(){
        for (int i = 0; i < 5; i++) {
            List<Passage> passages = passageService.getRandomPassage(null,false,"");
            for (Passage pa :passages){
                System.out.println(pa.getTitle());
            }
            System.out.println("--------------------------------------------------");
        }
    }
}
