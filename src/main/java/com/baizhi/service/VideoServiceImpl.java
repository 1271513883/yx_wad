package com.baizhi.service;

import com.baizhi.annotation.DeleteCache;
import com.baizhi.dao.VideoDao;

import com.baizhi.entity.Category;
import com.baizhi.entity.User;
import com.baizhi.entity.Video;
import com.baizhi.util.AliYun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Transactional
public class VideoServiceImpl implements VideoService{
    @Autowired
    private VideoDao videoDao;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryAllPage(int page, int rows) {
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        //查询数据总条数
        int records = videoDao.selecttotalCount();
        map.put("records",records);

        //总页数=总条数/每页展示条数  除不尽+1
        int total = records/rows;
        if (records%rows!=0){
            total++;
        }
        map.put("total",total);

        List<Video> list = videoDao.queryAllByPage((page-1)*rows,rows);
        map.put("data", list);

        return map;
    }
    @DeleteCache
    @Override
    public void add(String coverPath,String videoPath,String title,String brief,String id) {
        Category category=new Category();
        category.setId(id);
        Video video = new Video(UUID.randomUUID().toString(),title,brief,coverPath,videoPath,new Date(),category,null,null);
        videoDao.add(video);
    }

    @DeleteCache
    @Override
    public void delete(String id, String videoPath,String coverPath) {
        int i = videoPath.lastIndexOf("/");
        String fileName = videoPath.substring(i+1);
        AliYun.deleteFile(fileName,"video/");

        int i1 = coverPath.lastIndexOf("/");
        String fileName2 = coverPath.substring(i1+1);
        AliYun.deleteFile(fileName2,"video/");
        videoDao.delete(id);
    }
}
