package com.wuzhong.commons.result;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private T result;

    private Integer code;

    private String message;

    private boolean success = true;

    private Result() {
    }

    /**
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setResult(data);

        return result;
    }

    /**
     * @return
     */
    public static <T> Result<T> err(Integer code, String message) {
        Result<T> result = new Result<>();
        result.code = code;
        result.message = message;
        result.success = false;
        return result;
    }

    public String toJSONString(){
        return JSON.toJSONString(this);
    }


}
