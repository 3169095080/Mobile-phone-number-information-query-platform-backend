package org.example.phonelocatebackend.service;

import org.example.phonelocatebackend.model.entity.PhoneLocationInfo;

/**
 * 归属地信息查询服务
 */
public interface PhoneLocationInfoService {

    /**
     * 验证手机号是否有效
     *
     * @param phoneNumber 手机号
     * @return 验证状态码
     */
    int validPhoneNumber(String phoneNumber);

    /**
     * 缓存手机号归属地信息
     *
     * @param phoneLocateInfo 手机号归属地信息对象
     */
    void cachePhoneLocationInfo(PhoneLocationInfo phoneLocateInfo);

    /**
     * 获取手机号所属地信息
     *
     * @param phoneNumber 手机号
     * @return 手机号所属地信息
     */
    String getLocation(String phoneNumber);
}
