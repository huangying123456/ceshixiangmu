package com.youhujia.solar.domain.common;

import com.youhujia.halo.common.YHJExceptionCode;

/**
 * Created by cman on 12/22/16.
 */
public enum SolarExceptionCodeEnum implements YHJExceptionCode {

    UNKNOWN_ERROR(40001, "unknown error", "未知错误,请稍后重试"),
    PARAM_ERROR(40002, "params error", "参数错误");

    private Integer code;
    private String message4Log;
    private String message4Show;

    SolarExceptionCodeEnum(Integer code, String message4Log, String message4Show) {
        this.code = code;
        this.message4Log = message4Log;
        this.message4Show = message4Show;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage4Log() {
        return this.message4Log;
    }

    public String getMessage4Show() {
        return this.message4Show;
    }
}
