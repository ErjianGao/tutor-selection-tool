package com.erjiangao.tutorselectiontool.controller;

import com.erjiangao.tutorselectiontool.entity.User;
import com.erjiangao.tutorselectiontool.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/")
@Slf4j
public class LoginController {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Map postLogin(@RequestBody Map<String, String> user) {
        // ofNullable可以返回空对象
        User u = userService.getUser(user.get("id"));
        if (u == null || encoder.matches(user.get("password"), u.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名密码错误");
        } else {
            log.debug("登录成功");
        }
        return Map.of("user", u);
    }
}
