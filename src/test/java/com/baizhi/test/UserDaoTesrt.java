package com.baizhi.test;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@SpringBootTest
public class UserDaoTesrt {
    @Autowired
    private UserDao ud;

    @Test
    public void testQueryRange(){

        //1 0 1 2   2页 3
        List<User> list = ud.queryAllPage(0,2);
        for (User user : list) {
            System.out.println(user);
        }
    }
}

