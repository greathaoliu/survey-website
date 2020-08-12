package com.zju.service;

import com.zju.dao.UserDAO;
import com.zju.entity.User;
import com.zju.result.Result;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserManagementService {
    final UserDAO userDAO;

    public UserManagementService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Result login(String username, String password) {
        String correct_password = userDAO.findDistinctByUsername(username).getPassword();
        if (!password.equals(correct_password)) {
            return new Result(400); // 登陆失败
        } else {
            return new Result(200);
        }
    }

    public Result register(String username, String password, String email) {
        if (userDAO.findDistinctByUsername(username) != null) {
            return new Result(400); // 注册失败
        }
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setSigndate(new Date());
        userDAO.save(user);
        return new Result(200);
    }
}
