package com.zju.dao;

import com.zju.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerDAO extends JpaRepository<Answer, Integer>{
    List<Answer> findAllBySidAndQno(Integer sid, Integer qno);
}
