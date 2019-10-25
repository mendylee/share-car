package com.jinzhun.common.model;

public enum CodeEnum {
    SUCCESS(200),
    ERROR(1);

    private Integer code;
    CodeEnum(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
