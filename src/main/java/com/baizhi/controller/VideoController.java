package com.baizhi.controller;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.baizhi.entity.Video;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliYun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    private static final Logger log = LoggerFactory.getLogger(VideoController.class);
    @RequestMapping("queryAllPage")
    public Map<String,Object> queryAllPage(int page){
        int rows =2;

        return videoService.queryAllPage(page,rows);
    }

    @RequestMapping("add")
    public void add(MultipartFile video,String title,String brief,String id) throws IOException {
        log.debug("执行了");
        log.debug("标题:" + title);
        log.debug("描述:" + brief);
        log.debug("二级类别的id:" + id);
        String fileName = AliYun.upload(video,"video/");
        String videoPath = "https://2101wad.oss-cn-beijing.aliyuncs.com/"+fileName;
        URL url = AliYun.cutVideo(fileName );
        String coverPath = "https://vsvsvs.oss-cn-beijing.aliyuncs.com/"+AliYun.uploadStream(url, fileName );
        videoService.add(coverPath,videoPath,title,brief,id);


    }

    @RequestMapping("delete")
    public void delete(String coverPath,String id,String videoPath){
        videoService.delete(id,videoPath,coverPath);
    }
}
