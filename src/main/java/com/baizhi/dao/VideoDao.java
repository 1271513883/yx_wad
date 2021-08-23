package com.baizhi.dao;

import com.baizhi.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoDao {
    List<Video> queryAllByPage(@Param("start") int start, @Param("end") int end);

    int selecttotalCount();

    void add(Video video);

    void delete(String id);
}
