package com.zju.entity;

import lombok.Data;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "survey")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int sid;
    private String username; // 创建者名字
    @Lob
    private String sDescription;
    private String sTitle;
    private Date starttime;
    private Date endtime;
    private Integer status; // 0 degisn, 1 fill, 2 finish
    private Integer fillType; // 0 注册用户 1 每天填写n次 2 总共可填写n次
    private Integer fillnum; // 每天填写次数
    private Integer fillCount; // 当前填写次数
}
