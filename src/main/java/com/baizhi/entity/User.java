package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
  @Excel(name="ID")
  private Integer id;
  @Excel(name="用户名")
  private String usersname;
  @Excel(name="头像",type=2,width = 40 , height = 40)
  private String headimg;
  @Excel(name="手机号")
  private String phone;
  @Excel(name="简介")
  private String brief;
  @Excel(name="状态")
  private Integer status;
  @Excel(name="导出时间",format = "yyyy年MM月dd日",width = 20)
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date createdate;

  private  String sex;
  public User(Integer id) {
    this.id = id;
  }
}
