package com.zju.dao;

import com.zju.entity.Fill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FillDAO extends JpaRepository<Fill, Integer>{
    List<Fill> findAllByUsername(String username);
    List<Fill> findAllByIp(String IP);
    List<Fill> findAllByIpAndSid(String IP, Integer sid);
}
