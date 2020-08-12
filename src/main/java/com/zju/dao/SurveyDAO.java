package com.zju.dao;

import com.zju.entity.Survey;
import com.zju.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SurveyDAO extends JpaRepository<Survey, Integer>{
//    List<Survey> findAllByUid(Integer uid);
    List<Survey> findAllByUsername(String username);

    @Modifying
    @Transactional
    void deleteDistinctBySid(Integer sid);

    Survey findDistinctBySid(Integer sid);

    @Modifying
    @Transactional
    @Query("update survey set status = :status where sid = :sid")
    void updateSurveyStatus(@Param("sid")Integer sid,@Param("status") Integer status);

    @Modifying
    @Transactional
    @Query("update survey set fillCount = fillCount + 1 where sid = :sid")
    void incFillCountBySid(@Param("sid")Integer sid);
}
