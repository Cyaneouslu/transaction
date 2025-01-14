package com.xiaolu.transaction.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * @author wangxiaolu
 */
@RestControllerAdvice
public class ControllerAdviceHandle {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException ex) {
        // 返回状态码501和异常消息
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_IMPLEMENTED);
    }
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<String> handleSystemException(BusinessException ex) {
        // 返回状态码500和异常消息
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
