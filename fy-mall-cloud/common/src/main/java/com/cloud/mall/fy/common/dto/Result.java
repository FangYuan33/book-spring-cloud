package com.cloud.mall.fy.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private static final String DEFAULT_FAIL_MESSAGE = "FAIL";
    private static final int RESULT_CODE_SUCCESS = 200;
    private static final int RESULT_CODE_SERVER_ERROR = 500;

    /**
     * 业务码，比如成功、失败、权限不足等 code，可自行定义
     */
    @ApiModelProperty("返回码")
    private final int resultCode;
    /**
     * 返回信息，后端在进行业务处理后返回给前端一个提示信息，可自行定义
     */
    @ApiModelProperty("返回信息")
    private final String message;
    /**
     * 数据结果，泛型，可以是列表、单个对象、数字、布尔值等
     */
    @ApiModelProperty("返回数据")
    private T data;

    private Result(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public Result(int resultCode, String message, T data) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(RESULT_CODE_SUCCESS, DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> Result<T> success(String msg) {
        return new Result<>(RESULT_CODE_SUCCESS, msg);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(RESULT_CODE_SUCCESS, DEFAULT_SUCCESS_MESSAGE, data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(RESULT_CODE_SERVER_ERROR, DEFAULT_FAIL_MESSAGE);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(RESULT_CODE_SERVER_ERROR, msg);
    }

}
