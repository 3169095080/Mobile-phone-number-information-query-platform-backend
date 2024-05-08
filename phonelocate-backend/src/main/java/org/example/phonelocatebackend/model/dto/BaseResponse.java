package org.example.phonelocatebackend.model.dto;

import lombok.Data;
import org.example.phonelocatebackend.model.enums.ErrorCode;

import java.io.Serializable;

/**
 * 公共响应类
 *
 * @param <T> 泛型类型，表示响应数据的类型
 */
@Data
public class BaseResponse<T> implements Serializable {

    /**
     * 错误码
     */
    private int code;

    /**
     * 响应数据
     */
    private transient T data;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 构造一个带有错误码、响应数据和错误信息的 BaseResponse 对象
     *
     * @param code    错误码
     * @param data    响应数据
     * @param message 错误信息
     */
    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 构造一个带有错误码和响应数据的 BaseResponse 对象
     *
     * @param code 错误码
     * @param data 响应数据
     */
    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    /**
     * 构造一个带有 ErrorCode 对象的 BaseResponse 对象
     *
     * @param errorCode 错误码枚举对象
     */
    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}