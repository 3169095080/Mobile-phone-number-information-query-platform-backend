package org.example.phonelocatebackend.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 手机号信息
 */
@Data
public class PhoneLocationInfo implements Serializable {

    /**
     * 电话号
     */
    private String phoneNumber;

    /**
     * 所属省份
     */
    private String province;

    /**
     * 所属城市
     */
    private String city;

    private static final long serialVersionUID = 1L;
}
