package com.hotwave.clubv2.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author Kyrie
 * @version 1.0.0
 * @Description 相应类
 * @date 2022-09-04 18:40:00
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseResult<T> {

    private Integer code;

    private String msg;

    private T data;


    public ResponseResult(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(Integer code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
