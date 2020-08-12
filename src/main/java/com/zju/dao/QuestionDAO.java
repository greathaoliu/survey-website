package com.zju.dao;

import com.zju.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionDAO extends JpaRepository<Question, Integer>{
    List<Question> findAllBySid(Integer sid);
    Question findDistinctBySidAndQno(Integer sid, Integer qno);

    @Modifying
    @Transactional
    void deleteDistinctBySid(Integer sid);
}
