package com.erjiangao.tutorselectiontool.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class ExceptionController {
    @ApiOperation("属性校验失败异常")
    // 声明要处理哪一个异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    // 相应的状态码
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    // 将异常直接注入
    public Map handleValidException(MethodArgumentNotValidException exception) {
        StringBuilder strBuilder = new StringBuilder();
        // getBindingResult: 获取绑定的结果
        // getFieldErrors: 获取校验属性失败信息
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(e -> {
                    // getField: 校验失败属性名称
                    // getDefaultMessage: 失败描述信息
                    strBuilder.append(e.getField());
                    strBuilder.append(": ");
                    strBuilder.append(e.getDefaultMessage());
                    strBuilder.append("; ");
                });
        return Map.of("message", strBuilder.toString());
    }

    @ApiOperation("请求地址的类型失败")
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception,
            HttpServletRequest request) {
        // 拿到请求路径和请求值
        String msg = request.getRequestURI() +
                ": " + "请求地址参数" + exception.getValue() + "错误";
        return Map.of("message", msg);
    }

    @ApiOperation("jackson json类型转换异常")
    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handleInvalidFormatException(InvalidFormatException exception) {
        StringBuilder strBuilder = new StringBuilder();
        exception.getPath()
                .forEach(p -> {
                    strBuilder.append("属性");
                    strBuilder.append(p.getFieldName());
                    strBuilder.append("，您输入的值：").append(exception.getValue());
                    strBuilder.append(", 类型错误！");
                });
        return Map.of("message", strBuilder.toString());
    }

    @ApiOperation("方法级参数校验失败异常")
    // 方法级抛出异常后依然通过全局异常处理捕获，按声明的信息进行反馈
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handleConstraintViolationException(ConstraintViolationException exception) {
        StringBuilder strBuilder = new StringBuilder();
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        violations.forEach(v -> {
            strBuilder.append(v.getMessage() + "; ");
        });
        return Map.of("message", strBuilder.toString());
    }
}
