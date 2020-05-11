package com.raindrop.common.Constant;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum HttpResponseCode {
    NOT_FIND(404, "请求异常"),
    NO_AUTHORITY(403, "无权限查看");

    private Integer code;
    private String description;
}
