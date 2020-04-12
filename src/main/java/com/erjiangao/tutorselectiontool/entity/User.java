package com.erjiangao.tutorselectiontool.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
// 继承时使用的建表策略，分别生成父表和子表
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    public enum Role {
        STUDENT, TEACHER, ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // this is the username needed to be input
    @Column(unique = true)
    @NotBlank(message = "登录名不能为空")
    @Size(min = 6, message = "学号长度必须大于等于")
    private String identityNo;

    @NotBlank(message = "姓名不能为空")
    private String name;

    // 返回对象时忽略密码属性
    @Size(min = 6, message = "用户密码长度必须不少于{min}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // 角色同样不需要返回给前端，因为前端会通过密钥对获取角色信息
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Role role;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false,
            insertable = false)
    private LocalDateTime insertTime;
}
