package com.baizhi.service;


import com.baizhi.entity.User;

import java.util.Map;

public interface UserService {
    Map<String, Object> queryAllPage(int page, int rows);

    void update(User user);

    void add(String headimg,String usersname,String phone,String brief);

    void delete(Integer id,String headimg);

      boolean printInfo();

    Map<String, Object> queryUserSexCount();
}
