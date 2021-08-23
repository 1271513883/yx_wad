package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.vo.MonthAndCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    List<User>  queryAllPage(@Param("start") int start, @Param("end") int end);

    int selecttotalCount();

    void update(User user);

    void add(User user);

    void delete(Integer id);

    List<User> queryAll();

    List<MonthAndCount> queryMonthCount(String sex);
}
