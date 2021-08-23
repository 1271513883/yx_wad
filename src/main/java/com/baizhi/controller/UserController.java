package com.baizhi.controller;


import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.AliYun;
import com.baizhi.vo.MonthAndCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;



    @RequestMapping("queryAllPage")
    public Map<String, Object> queryAllPage(int page) {
        int rows =3;
        return userService.queryAllPage(page, rows);
    }

    @RequestMapping("updateStatus")
    public void updateStatus(@RequestBody User user){

        log.info("controller接收数据:{}",user);

        userService.update(user);
    }

    @RequestMapping("add")
    public void add(MultipartFile photo, String usersname, String phone, String brief ) throws Exception{
           String fileName = AliYun.upload(photo,"");
           String headimg = "https://2101wad.oss-cn-beijing.aliyuncs.com/"+fileName;
           userService.add(headimg,usersname,phone,brief);
    }

    @RequestMapping("delete")
    public void delete(Integer id,String headimg){
          userService.delete(id,headimg);
    }

    @RequestMapping("printInfo")
    public boolean printInfo() throws IOException {
        return userService.printInfo();
    }

   @RequestMapping("/registCount")
    public Map<String, Object> registCount() {
         return userService.queryUserSexCount();


   }
}