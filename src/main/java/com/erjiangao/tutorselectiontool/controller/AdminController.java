package com.erjiangao.tutorselectiontool.controller;

import com.erjiangao.tutorselectiontool.component.ResponseComponent;
import com.erjiangao.tutorselectiontool.entity.Admin;
import com.erjiangao.tutorselectiontool.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@Slf4j
public class AdminController {
    @Autowired
    private ResponseComponent responseComponent;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;

    // 用户首页
    @GetMapping("")
    public Map welcome() {
        log.debug("{}", responseComponent.getIdentityNo());
        log.debug("{}", responseComponent.getRole());
        return Map.of("msg", "欢迎光临，管理员");
    }

    @PatchMapping("/password")
    public Map updatePassword(@RequestBody Map<String, String> admin) {
        String oldPassword = admin.get("oldPassword");
        String newPassword = admin.get("newPassword");
        String confirmNewPassword = admin.get("confirmNewPassword");
        if (!encoder.matches(oldPassword,
                userService.getUser(responseComponent.getIdentityNo()).getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "您输入的旧密码错误");
        }
        if (!newPassword.equals(confirmNewPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "你前后两次输入密码不同");
        }
        Admin a = userService.updatePassword(responseComponent.getIdentityNo(), newPassword);
        return Map.of("admin", a);
    }
}
