package com.zju.result;

import lombok.Data;

@Data
public class Result {
    //响应码
    private int code;
    private String message;

    public Result(int code) {
        this.code = code;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
