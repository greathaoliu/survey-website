package com.zju.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer aid;
    private String username; // 作答者用户名
    private String ip; // 作答者ip
    private Integer sid; // 所属问卷
    private Integer qno; // 对应题目在问卷中位置
    private String qType; // 问题类型

    private Integer aOption;
    private String aOptions;
    private Integer aInt;
    private Float aFloat;
    @Lob
    private String aText;
    private Integer aRate;
}
