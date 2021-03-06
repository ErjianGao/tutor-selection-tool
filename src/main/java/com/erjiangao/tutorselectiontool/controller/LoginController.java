package com.erjiangao.tutorselectiontool.controller;

import com.erjiangao.tutorselectiontool.component.EncryptComponent;
import com.erjiangao.tutorselectiontool.component.MyToken;
import com.erjiangao.tutorselectiontool.entity.User;
import com.erjiangao.tutorselectiontool.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

@RestController
// json数据进行传递的规范
@RequestMapping("/api/")
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


    @PostMapping("login")
    @ApiOperation("登录")
    @ApiResponses({@ApiResponse(code = 200, message = "返回角色编码")})
    public String postLogin(@RequestBody User user, HttpServletResponse response) {
        // ofNullable可以返回空对象
        User u = userService.getUser(user.getIdentityNo());
        if (u == null || !encoder.matches(user.getPassword(), u.getPassword())) {
            response.setHeader(MyToken.AUTHORIZATION, "");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名密码错误");
        }
        log.debug("{} login successfully", u.getName());
        // 此时应该将用户的id（主键存入token）
        MyToken token = new MyToken(u.getId(), u.getRole());
        String auth = encryptComponent.encryptToken(token);
        // 放到响应头中传给客户端，使用HttpRespond
        response.setHeader(MyToken.AUTHORIZATION, auth);
        // 前端需要知道用户的身份
        String roleCode = "";
        if (u.getRole().equals(User.Role.ADMIN)) {
            roleCode = roleAdmin;
        } else if (u.getRole().equals(User.Role.TEACHER)) {
            roleCode = roleTeacher;
        } else if (u.getRole().equals(User.Role.STUDENT)) {
            roleCode = roleStudent;
        }
        return roleCode;
    }
}
