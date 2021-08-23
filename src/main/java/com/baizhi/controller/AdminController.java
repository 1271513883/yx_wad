package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    private Logger log = LoggerFactory.getLogger(AdminController.class);
    @RequestMapping("/login")
    public Map<String,Object> login(@RequestBody Admin admin){
        Map<String,Object> map = new HashMap<>();
        log.info("执行了");
        log.info(admin.toString());
        Admin admin1 = adminService.findAllByName(admin.getUsername());

        map.put("flag", false); //登陆失败
        if (admin1!=null){
            //判断密码
            if(admin.getPassword().equals(admin1.getPassword())){
                //用户名 密码 都正确
                map.put("flag",true);//登陆成功
                map.put("admin", admin1);
            }else{
                //map.put("flag",false);//密码错误
                map.put("msg", "密码错误！！！");
            }
        }else{

            map.put("msg", "用户名不存在!!!");
        }
        return map;
    }
}
