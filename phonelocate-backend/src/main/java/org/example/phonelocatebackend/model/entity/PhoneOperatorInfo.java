package org.example.phonelocatebackend.model.entity;

import lombok.Data;
import org.example.phonelocatebackend.model.enums.PhoneOperatorEnum;

import java.io.Serializable;

/**
 * 手机号的营业厅实体
 */
@Data
public class PhoneOperatorInfo implements Serializable {

    /**
     * 手机号前三位数字
     */
    private String phoneTopThreeNumber;

    /**
     * 手机号供应商
     */
    private PhoneOperatorEnum operator;

    private static final long serialVersionUID = 1L;
}

