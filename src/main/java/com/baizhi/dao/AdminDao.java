package com.baizhi.dao;

import com.baizhi.entity.Admin;

public interface AdminDao {
    Admin queryAllByUserName(String username);
}
