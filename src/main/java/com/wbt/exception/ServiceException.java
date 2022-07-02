package com.wbt.exception;


import lombok.Getter;


/**
 * @author 15236
 * 自定义异常
 */
@Getter
public class ServiceException extends RuntimeException{
    private Integer code;

    public ServiceException(Integer code,String msg){
        super(msg);
        this.code=code;
    }
}
