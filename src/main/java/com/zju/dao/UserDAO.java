package com.zju.dao;

import com.zju.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// JPA太方便了吧
public interface UserDAO extends JpaRepository<User, Integer>{
    User findDistinctByUsername(String username);
}
