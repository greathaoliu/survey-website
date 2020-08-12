package com.zju.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer qid;
    private Integer sid; // 所属问卷
    private Integer qno; // 问题在问卷中的编号
    @Lob
    private String qDescription; // 题干
    private String qOptions; // 如果是选择题，选项分别是什么
    private Boolean qIsRequired; // 是否必答
    private String qType; // single_choice, multi_choice, single_text, text, int, float, rate
    // private String qSubsequents; // 每个选项对应的后续问题，二维List Json
    private Integer maxrate;
}
