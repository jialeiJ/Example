package com.vienna.jaray.globalExceptionHandler;

import com.vienna.jaray.common.ResponseResult;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String BAD_REQUEST_MSG = "参数检验不通过";

    //处理 form data方式调用接口校验失败抛出的异常
    @ExceptionHandler(BindException.class)
    public ResponseResult bindExceptionHandler(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String tips = fieldErrors.stream().map(o -> o.getDefaultMessage()).collect(Collectors.joining("; "));

        return ResponseResult.fail().add("tip", tips);
//        return new ResultInfo().success(HttpStatus.BAD_REQUEST.value(), BAD_REQUEST_MSG, collect);
    }

    //  处理 json 请求体调用接口校验失败抛出的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String tips = fieldErrors.stream().map(o -> o.getDefaultMessage()).collect(Collectors.joining("; "));

        return ResponseResult.fail().add("tip", tips);
    }

    //  处理单个参数校验失败抛出的异常
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult constraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        String tips = constraintViolations.stream().map(o -> o.getMessage()).collect(Collectors.joining("; "));

        return ResponseResult.fail().add("tip", tips);
    }

    // 处理以上处理不了的其他异常
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
        return ResponseResult.fail().add("tip", e.getMessage());
    }
}
