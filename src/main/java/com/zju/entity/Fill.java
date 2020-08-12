package com.zju.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

// 用户填写问卷关系表，relation
@Data
@Entity(name = "fill")
public class Fill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer fid;
    private Integer sid; // 问卷id
    private String ip;
    private String username; // 可选，如果没登录就填写为null
    private Date fDate;
}
