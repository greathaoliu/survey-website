package com.zju.controller;

import com.zju.service.UserManagementService;
import com.zju.result.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import com.zju.entity.User;


@RestController
public class UserController {
    final UserManagementService userManagementService;

    public UserController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @CrossOrigin
    @PostMapping("api/login")
    public Result login(@RequestBody User requestUser) {
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        String password = requestUser.getPassword();

        return userManagementService.login(username, password);
    }

    @CrossOrigin
    @PostMapping("api/register")
    public Result register(@RequestBody User requestUser) {
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        String password = requestUser.getPassword();
        String email = requestUser.getEmail();
        return userManagementService.register(username, password, email);
    }
}