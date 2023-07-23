package com.jty.exception;

import com.jty.enums.AppHttpCodeEnum;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
public class SystemException extends RuntimeException {

    private int code;

    private String msg;

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
