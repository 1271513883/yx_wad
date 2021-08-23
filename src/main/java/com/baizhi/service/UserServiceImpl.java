package com.baizhi.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.annotation.DeleteCache;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.util.AliYun;
import com.baizhi.vo.MonthAndCount;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryAllPage(int page, int rows) {
        Map<String, Object> map = new HashMap<>();

        map.put("page",page);
        //查询数据总条数
        int records = userDao.selecttotalCount();
        map.put("records",records);

        //总页数=总条数/每页展示条数  除不尽+1
        int total = records/rows;
        if (records%rows!=0){
            total++;
        }
        map.put("total",total);

        List<User> list = userDao.queryAllPage((page-1)*rows,rows);
        map.put("data", list);

        return map;
    }
    @DeleteCache
    @Override
    public void update(User user) {
        if(user.getStatus()==1){
            user.setStatus(0);
        }else {
            user.setStatus(1);
        }
        userDao.update(user);
    }

    @DeleteCache
    @Override
    public void add(String headimg, String usersname, String phone, String brief) {
        User user = new User(null,usersname,headimg,phone,brief, 0,new Date(),null);
        userDao.add(user);
    }

    @Override
    public boolean printInfo() {
        List<User> users = userDao.queryAll();
        List<String> localFiles=new ArrayList<>();
        for (User user : users) {
            String localFile = AliYun.download(user.getHeadimg());
            user.setHeadimg(localFile);
            localFiles.add(localFile);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息", "用户"), User.class, users);
        try {
            workbook.write(new FileOutputStream(new File("E:/easypoi.xls")));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        for (String localFile : localFiles) {
            File file = new File(localFile);
            file.delete();
        }
        return true;
    }

    @DeleteCache
    @Override
    public void delete(Integer id,String headimg) {
        int i =  headimg.lastIndexOf("/");
        String fileName = headimg.substring(i+1);
        AliYun.deleteFile(fileName,"");

        userDao.delete(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryUserSexCount() {

        System.out.println("执行了");
        List<String> data = new ArrayList<>();
        //得到男生每个月注册人数
        List<Integer> manCount = new ArrayList<>();
        List<Integer> womanCount = new ArrayList<>();

        List<MonthAndCount> man = userDao.queryMonthCount("男");

        List<MonthAndCount> nv = userDao.queryMonthCount("女");
        /*
             MonthAndCount    7 1
             MonthAndCount    8 2
         */

//        向data 集合中存储 1~12 月
        for(int i=1; i<=12; i++){  //  1
            data.add(i+"月");

            //女生
            boolean flag2 = false;
            for (MonthAndCount monthAndCount : nv) {
                if( monthAndCount.getMonth() == i){ // 7 == 7
                    womanCount.add(monthAndCount.getCount());
                    flag2 = true;
                }
            }
            if(!flag2){
                womanCount.add(0);
            }

            //男生
            boolean flag = false;
            for (MonthAndCount monthAndCount : man) {
                if( monthAndCount.getMonth() == i){ // 7 == 7
                    manCount.add(monthAndCount.getCount());
                    flag = true;
                }
            }
            if(!flag){
                manCount.add(0);
            }
        }

        //查询男生每月注册人数
        //查询女生每月注册人数

        //  存储了   月份    男生人数  女生人数
        Map<String,Object> map = new HashMap<>();
        map.put("data", data);
        map.put("manCount", manCount);
        map.put("womanCount", womanCount);

        return map;
    }
}
