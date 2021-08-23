package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.dao.VideoDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.Set;

@SpringBootTest
class YxWadApplicationTests {

    @Autowired
    private AdminDao adminDao;
    @Autowired
    private VideoDao videoDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
        Admin admin = adminDao.queryAllByUserName("wad");
        System.out.println(admin);
    }

    @Test
    void contextLoads1() {
        List<Video> list = videoDao.queryAllByPage(0, 3);
        for (Video video : list) {
            System.out.println(video);
        }
    }

    @Test
    public void testRedis() {

        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set("tom", "123");
        System.out.println(valueOperations.get("tom"));
        Set<String> keys = stringRedisTemplate.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
    }
}

