package com.erjiangao.tutorselectiontool.config;

import com.erjiangao.tutorselectiontool.entity.Teacher;
import com.erjiangao.tutorselectiontool.interceptor.AdminInterceptor;
import com.erjiangao.tutorselectiontool.interceptor.LoginInterceptor;
import com.erjiangao.tutorselectiontool.interceptor.TeacherInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private AdminInterceptor adminInterceptor;
    @Autowired
    private TeacherInterceptor teacherInterceptor;

    @Override
    // add interceptors
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/login");
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/api/admin/**");
        registry.addInterceptor(teacherInterceptor)
                .addPathPatterns("/api/teacher**");

    }
}
