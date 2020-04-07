package com.erjiangao.tutorselectiontool.component;

import com.erjiangao.tutorselectiontool.entity.Admin;
import com.erjiangao.tutorselectiontool.entity.User;
import com.erjiangao.tutorselectiontool.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitializingBean implements org.springframework.beans.factory.InitializingBean {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserService userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        String identifyNo = "erjiangao";
        User user = userService.getUser(identifyNo);
        if (user == null) {
            Admin admin = new Admin();
            admin.setIdentityNo(identifyNo);
            admin.setName("Eric Gao");
            admin.setRole(User.Role.ADMIN);
            // 设置初始密码，系统创建后更改
            admin.setPassword(encoder.encode(identifyNo));
            // 持久层添加管理员
            userService.addAdmin(admin);
        }
    }
}
