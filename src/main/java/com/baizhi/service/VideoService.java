package com.baizhi.service;


import java.util.Map;

public interface VideoService {
    Map<String, Object> queryAllPage(int page, int rows);

     void add(String coverPath,String videoPath,String title,String brief,String id);

    void delete(String id,String videoPath,String coverPath);
}
