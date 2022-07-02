package com.wbt.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * @author 15236
 * 接口统一返回包装类
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult {
   private Integer code;
   private String msg;
   private Object data;
   public  static ResponseResult success(){
       return new ResponseResult(HttpStatus.OK.value(), "",null);
   }
   public  static ResponseResult success(Object data){
      return new ResponseResult(HttpStatus.OK.value(), "",data);
   }
   public  static ResponseResult error(){
      return new ResponseResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统错误",null);
   }
   public  static ResponseResult error(Integer code,String msg){
      return new ResponseResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "msg",null);
   }
}
