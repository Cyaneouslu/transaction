package com.xiaolu.transaction.config;

/**
 * @author wangxiaolu
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}