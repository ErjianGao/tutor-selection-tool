package com.erjiangao.tutorselectiontool.Interceptor;

import com.erjiangao.tutorselectiontool.component.EncryptComponent;
import com.erjiangao.tutorselectiontool.component.MyToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private EncryptComponent encryptors;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // map: 如果当前 Optional 为 Optional.empty，则依旧返回 Optional.empty；否则返回一个新的 Optional，该 Optional 包含的是：函数 mapper 在以 value 作为输入时的输出值。
        // ifPresentOrElse()与ifPresent()的区别是当元素为空时会调用第二个参数
        // 如果在执行map方法的时候出异常，那么异常会在解密时抛出，未授权
        Optional.ofNullable(request.getHeader(MyToken.AUTHORIZATION))
                .map(auth -> encryptors.decryptToken(auth))
                .ifPresentOrElse(token -> {
                    // 只要能拿到request对象就能知道人员是谁
                    request.setAttribute(MyToken.IDENTITY_NO, token.getIdentityNo());
                    request.setAttribute(MyToken.ROLE, token.getRole());
                }, () -> {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "您还没有登录");
                });
        return true;
    }
}
