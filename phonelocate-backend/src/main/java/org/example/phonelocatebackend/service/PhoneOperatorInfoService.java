package org.example.phonelocatebackend.service;

import org.example.phonelocatebackend.model.dto.BaseResponse;
import org.example.phonelocatebackend.model.entity.PhoneOperatorInfo;
import org.example.phonelocatebackend.model.enums.PhoneOperatorEnum;

/**
 * 供应商查询服务
 *
 * @author Administrator
 */
public interface PhoneOperatorInfoService {
    /**
     * 根据手机号获取运营商
     *
     * @param phoneTopThreeNumber 手机号
     * @return 运营商枚举
     */
    PhoneOperatorEnum getOperator(String phoneTopThreeNumber);

    /**
     * 校验手机号是否合法
     *
     * @param phoneTopThreeNumber 手机号前三位数字
     * @return 验证状态码
     */
    int validPhoneTopThreeNumber(String phoneTopThreeNumber);

    /**
     * 缓存手机号供应商信息
     *
     * @param phoneOperatorInfo 手机号供应商信息对象
     */
    void cachePhoneOperatorInfo(PhoneOperatorInfo phoneOperatorInfo);
}
