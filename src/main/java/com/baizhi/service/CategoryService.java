package com.baizhi.service;

import com.baizhi.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    List<Category> queryByLevels(int Levels);

    List<Category> queryByParentId(String id);

    void saveLevel(Category category);

    Map<String,Object> delete(String id);

    void saveFirst(Category category);


}
