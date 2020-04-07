package com.erjiangao.tutorselectiontool.controller;

import com.erjiangao.tutorselectiontool.component.EncryptComponent;
import com.erjiangao.tutorselectiontool.component.MyToken;
import com.erjiangao.tutorselectiontool.entity.User;
import com.erjiangao.tutorselectiontool.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
// json数据进行传递的规范
@RequestMapping("/api")
@Slf4j
public class LoginController {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserService userService;
    @Autowired
    private EncryptComponent encryptComponent;
    @Value("${my.admin}")
    private String roleAdmin;
    @Value("${my.teacher}")
    private String roleTeacher;
    @Value("${my.student}")
    private String roleStudent;


    @PostMapping("/login")
    public Map postLogin(@RequestBody User user, HttpServletResponse response) {
        // ofNullable可以返回空对象
        User u = userService.getUser(user.getIdentityNo());
        if (u == null || !encoder.matches(user.getPassword(), u.getPassword())) {
            response.setHeader(MyToken.AUTHORIZATION, "");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名密码错误");
        }
        log.debug("{} login successfully", u.getName());
        MyToken token = new MyToken(u.getIdentityNo(), u.getRole());
        String auth = encryptComponent.encryptToken(token);
        // 放到响应头中传给客户端，使用HttpRespond
        response.setHeader(MyToken.AUTHORIZATION, auth);
        // 前端需要知道用户的身份
        String roleCode = "";
        if (u.getRole().equals(User.Role.ADMIN)) {
            roleCode = roleAdmin;
        }
        if (u.getRole().equals(User.Role.TEACHER)) {
            roleCode = roleTeacher;
        }
        if (u.getRole().equals(User.Role.STUDENT)) {
            roleCode = roleStudent;
        }
        return Map.of("role", roleCode);
    }
}
