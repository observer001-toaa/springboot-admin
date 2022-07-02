package com.wbt.exception;

import com.wbt.common.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 15236
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseResult handle(ServiceException se){
        return ResponseResult.error(se.getCode(), se.getMessage());
    }
}
