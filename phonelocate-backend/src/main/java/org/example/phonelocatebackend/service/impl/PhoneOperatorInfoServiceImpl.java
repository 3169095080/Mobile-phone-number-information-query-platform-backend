package org.example.phonelocatebackend.service.impl;

import javax.annotation.Resource;
import org.example.phonelocatebackend.model.entity.PhoneOperatorInfo;
import org.example.phonelocatebackend.model.enums.PhoneOperatorEnum;
import org.example.phonelocatebackend.service.PhoneOperatorInfoService;
import org.example.phonelocatebackend.utils.RedisUtil;
import org.springframework.stereotype.Service;

/**
 * 供应商查询实现类
 *
 */
@Service
public class PhoneOperatorInfoServiceImpl implements PhoneOperatorInfoService {
    /**
     * 校验手机号是否合法
     *
     * @param phoneTopThreeNumber 手机号前三位数字
     * @return 验证状态码
     */
    @Override
    public int validPhoneTopThreeNumber(String phoneTopThreeNumber) {

        // 校验手机号是否为空
        if (phoneTopThreeNumber == null) {
            return 40004;
        }

        // 校验手机号前三位是否为数字
        if (!phoneTopThreeNumber.matches("\\d{3}")) {
            return 40003;
        }

        // 所有校验通过
        return 0;
    }

    /**
     * 根据手机号获取运营商
     *
     * @param phoneTopThreeNumber 手机号
     * @return 运营商枚举
     */
    @Override
    public PhoneOperatorEnum getOperator(String phoneTopThreeNumber) {
        // 根据手机号前三位确定运营商
        switch (phoneTopThreeNumber) {
            case "133":
            case "149":
            case "153":
            case "180":
            case "181":
            case "189":
            case "177":
                return PhoneOperatorEnum.TELECOM;
            case "134":
            case "135":
            case "136":
            case "137":
            case "138":
            case "139":
            case "147":
            case "150":
            case "151":
            case "152":
            case "157":
            case "158":
            case "159":
            case "178":
            case "182":
            case "183":
            case "184":
            case "187":
            case "188":
                return PhoneOperatorEnum.MOBILE;
            case "130":
            case "131":
            case "132":
            case "145":
            case "155":
            case "156":
            case "171":
            case "175":
            case "176":
            case "185":
            case "186":
                return PhoneOperatorEnum.UNICOS;
            default:
                return null;
        }
    }

    @Resource
    // 用于操作供应商缓存
    private RedisUtil redisUtil;

    @Resource
    // 用于设置缓存过期时间
    private Long defaultCacheTtl;
    /**
     * 缓存手机号的运营商
     *
     * @param phoneOperatorInfo 手机号运营商信息
     */
    @Override
    public void cachePhoneOperatorInfo(PhoneOperatorInfo phoneOperatorInfo) {
        redisUtil.set(phoneOperatorInfo.getPhoneTopThreeNumber(), phoneOperatorInfo);
        // 设置数据过期时间
        redisUtil.expire(phoneOperatorInfo.getPhoneTopThreeNumber(), defaultCacheTtl);
    }
}
