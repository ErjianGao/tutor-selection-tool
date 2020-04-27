package com.erjiangao.tutorselectiontool.component;

import com.erjiangao.tutorselectiontool.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyToken {
    // 按照约定，以“authorization”为键存放信息到Header中，这里使用常量，尽可能减少硬编码
    public static final String AUTHORIZATION = "authorization";
    public static final String UID = "uid";
    public static final String ROLE = "role";
    // public static final String
    // 令牌中包含身份校验的核心信息
    private int uid;
    private User.Role role;
}
