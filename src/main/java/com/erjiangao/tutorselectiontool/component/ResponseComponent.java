package com.erjiangao.tutorselectiontool.component;

import com.erjiangao.tutorselectiontool.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class ResponseComponent {
    public int getUid() {
        return (int) RequestContextHolder.currentRequestAttributes()
                .getAttribute("uid", RequestAttributes.SCOPE_REQUEST);
    }

    public User.Role getRole() {
        return (User.Role) RequestContextHolder.currentRequestAttributes()
                .getAttribute("role", RequestAttributes.SCOPE_REQUEST);
    }
}
