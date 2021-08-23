package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {

  private String id;
  private String cate_name;
  private Integer levels;
  private String parent_id;

  public Category(String id) {
    this.id = id;
  }
}
