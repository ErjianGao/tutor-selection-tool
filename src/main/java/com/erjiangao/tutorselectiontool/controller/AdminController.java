package com.erjiangao.tutorselectiontool.controller;

import com.erjiangao.tutorselectiontool.component.ResponseComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/")
@Slf4j
public class AdminController {
    @Autowired
    private ResponseComponent responseComponent;

    @GetMapping("welcome")
    public Map welcome() {
        log.debug("{}", responseComponent.getIdentityNo());
        log.debug("{}", responseComponent.getRole());
        return Map.of("msg", "欢迎光临，管理员");
    }
}
