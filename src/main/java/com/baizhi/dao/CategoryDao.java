package com.baizhi.dao;

import com.baizhi.entity.Category;

import java.util.List;

public interface CategoryDao {
     List<Category> queryByLevels(int Levels);

    List<Category> queryByParentId(String id);

    void saveLevel(Category category);
    void delete(String id);

    void saveFirst(Category category);

    void deleteFirst(String id);

    Category queryById(String id);
}
