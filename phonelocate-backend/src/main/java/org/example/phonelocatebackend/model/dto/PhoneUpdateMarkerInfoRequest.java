package org.example.phonelocatebackend.model.dto;

import lombok.Data;

@Data
public class PhoneUpdateMarkerInfoRequest {

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 标注类型
     */
    private String markType;
}
