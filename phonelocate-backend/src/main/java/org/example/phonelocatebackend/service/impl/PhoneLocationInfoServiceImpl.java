package org.example.phonelocatebackend.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.phonelocatebackend.model.entity.PhoneLocationInfo;
import org.example.phonelocatebackend.service.PhoneLocationInfoService;
import org.example.phonelocatebackend.utils.PhoneLocateUtil;
import org.example.phonelocatebackend.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 归属地信息查询服务实现类
 *
 */
@Service
public class PhoneLocationInfoServiceImpl implements PhoneLocationInfoService {

    private static final Logger logger = LoggerFactory.getLogger(PhoneLocationInfoServiceImpl.class);

    /**
     * 校验手机号格式
     *
     * @param phoneNumber 手机号
     * @return 验证状态码
     */
    @Override
    public int validPhoneNumber(String phoneNumber) {
        // 校验手机号位数
        if (phoneNumber.length() != 11) {
            return 40000;
        }

        // 校验手机号是否为数字
        if (!phoneNumber.matches("\\d{11}")) {
            return 40003;
        }

        // 所有校验通过
        return 0;
    }

    @Resource
    // 用于操作归属地缓存
    private RedisUtil redisUtil;

    @Resource
    // 用于设置缓存过期时间
    private Long defaultCacheTtl;

    /**
     * 缓存手机号归属地
     *
     * @param phoneLocateInfo 手机归属地信息
     */
    @Override
    public void cachePhoneLocationInfo(PhoneLocationInfo phoneLocateInfo) {
        redisUtil.set(phoneLocateInfo.getPhoneNumber(), phoneLocateInfo);
        // 设置数据过期时间
        redisUtil.expire(phoneLocateInfo.getPhoneNumber(), defaultCacheTtl);
    }

    @Resource
    // 用于查询手机号归属地
    PhoneLocateUtil phoneLocationUtil;

    /**
     * 缓存手机号的所属地
     *
     * @param phoneNumber 手机号
     * @return 所属地信息
     */
    @Override
    public String getLocation(String phoneNumber) {
        String jsonResponse =  phoneLocationUtil.getPhoneLocation(phoneNumber);
        // 解析响应的JSON字符串
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            JsonNode dataNode = rootNode.path("data");
            if (dataNode.isMissingNode()) {
                return null;
            }

            String province = dataNode.path("province").asText();
            String city = dataNode.path("city").asText();


            logger.info("phoneLocateInfo: " + province + " " + city);

            return province + " " + city;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
