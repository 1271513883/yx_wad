package com.baizhi.service;

import com.baizhi.annotation.DeleteCache;
import com.baizhi.dao.CategoryDao;
import com.baizhi.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryDao categoryDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryByLevels(int Levels) {
        return categoryDao.queryByLevels(Levels);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryByParentId(String id) {

        return categoryDao.queryByParentId(id);
    }
    @DeleteCache
    @Override
    public void saveLevel(Category category) {
        category.setId(UUID.randomUUID().toString());
        categoryDao.saveLevel(category);
    }


    @DeleteCache
    @Override
    public void saveFirst(Category category) {
        category.setId(UUID.randomUUID().toString());
        categoryDao.saveFirst(category);
    }
    @DeleteCache
    @Override
    public Map<String,Object> delete(String id) {
        Map<String,Object> map = new HashMap<>();
       Category category = categoryDao.queryById(id);
       Integer levels = category.getLevels();

       if(levels == 1){
        List<Category> categories = categoryDao.queryByParentId(id);
         if(categories.size()==0){
            categoryDao.delete(id);
            map.put("flag",true);
         }else{
            map.put("flag",false);
            map.put("msg","删除失败,有二级类别");
         }
       }else{
           categoryDao.delete(id);
       }
       return map;
    }
}
