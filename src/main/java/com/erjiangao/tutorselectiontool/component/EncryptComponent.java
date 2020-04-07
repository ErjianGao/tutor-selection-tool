package com.erjiangao.tutorselectiontool.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Configuration
public class EncryptComponent {
    // 加密解密组件
    // 序列化工具
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${my.secretKey}")
    private String secretKey;
    @Value("${my.salt}")
    private String salt;
    @Autowired
    private TextEncryptor textEncryptor;

    //单例模式创建TextEncryptor对象，避免反复创建
    @Bean
    public TextEncryptor getTextEncryptor() {
        return Encryptors.text(secretKey, salt);
    }

    /**
     * 将令牌加密
     *
     * @param token 令牌（这时刚刚在controller层创建好）
     * @return 加密令牌
     */
    public String encryptToken(MyToken token) {
        // 手动序列化会抛出异常
        try {
            String json = objectMapper.writeValueAsString(token);
            return textEncryptor.encrypt(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "服务端错误");
        }
    }

    public MyToken decryptToken(String auth) {
        try {
            // 先将加密令牌解密
            String json = textEncryptor.decrypt(auth);
            // 手动进行反序列化，需要捕获异常
            return objectMapper.readValue(json, MyToken.class);
            // 由于解密也有可能出现异常，所以同样进行捕获，设置捕获的异常类型为任意，
            // 这样不管在解密时出现什么问题等能够正确返回异常
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "您没有权限访问");
        }
    }
}
// package com.erjiangao.tutorselectiontool.component;
//
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.http.HttpStatus;
// import org.springframework.security.crypto.encrypt.Encryptors;
// import org.springframework.security.crypto.encrypt.TextEncryptor;
// import org.springframework.stereotype.Component;
// import org.springframework.web.server.ResponseStatusException;
//
// @Component
// @Slf4j
// public class EncryptComponent {
//     @Value("${my.secretKey}")
//     private String SecretKey;
//     @Value("${my.salt}")
//     private String salt;
//     @Autowired
//     private TextEncryptor encryptor;
//     @Autowired
//     private ObjectMapper objectMapper;
//
//     // 不声明为组件的话没法注入
//     @Bean
//     public TextEncryptor getTextEncryptor() {
//         // 添加密钥和盐值
//         return Encryptors.text(SecretKey, salt);
//     }
//
//     /**
//      * 将令牌加密
//      * @param token 令牌（这时刚刚在controller层创建好）
//      * @return 加密令牌
//      */
//     public String encryptToken(MyToken token) {
//         // 手动序列化会抛出异常
//         try {
//             String json = objectMapper.writeValueAsString(token);
//             return encryptor.encrypt(json);
//         } catch (JsonProcessingException e) {
//             e.printStackTrace();
//             throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "服务端错误");
//         }
//     }
//
//     public MyToken decryptToken(String auth) {
//         try {
//             // 先将加密令牌解密
//             String json = encryptor.decrypt(auth);
//             // 手动进行反序列化，需要捕获异常
//             return objectMapper.readValue(json, MyToken.class);
//             // 由于解密也有可能出现异常，所以同样进行捕获，设置捕获的异常类型为任意，
//             // 这样不管在解密时出现什么问题等能够正确返回异常
//         } catch (Exception e) {
//             e.printStackTrace();
//             throw new ResponseStatusException(HttpStatus.FORBIDDEN, "无权限");
//         }
//     }
// }
