package com.baizhi.controller;

import com.baizhi.entity.Category;
import com.baizhi.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
     @RequestMapping("queryByLevels")
    public List<Category> queryByLevels(int levels){

         return categoryService.queryByLevels(levels);
    }
    @RequestMapping("queryByParentId")
    public List<Category> queryByParentId(String id){

           return categoryService.queryByParentId(id);
    }
    @RequestMapping("save")
    public void save(@RequestBody Category category){
         categoryService.saveLevel(category);
    }
    @RequestMapping("delete")
    public Map<String,Object> delete(String id){
         return categoryService.delete(id);
    }

    @RequestMapping("saveFirst")
    public void saveFirst(@RequestBody Category category){
        categoryService.saveFirst(category);
    }

}
