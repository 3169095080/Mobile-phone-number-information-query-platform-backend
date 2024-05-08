package org.example.phonelocatebackend.controller;

import org.example.phonelocatebackend.model.dto.BaseResponse;
import org.example.phonelocatebackend.model.entity.PhoneLocationInfo;
import org.example.phonelocatebackend.model.enums.ErrorCode;
import org.example.phonelocatebackend.service.impl.PhoneLocationInfoServiceImpl;
import org.example.phonelocatebackend.utils.RedisUtil;
import org.example.phonelocatebackend.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 手机号归属地信息控制器
 */
@CrossOrigin
@RestController
@RequestMapping("/phone")
public class PhoneLocationInfoController {

    @Resource
    // 用于操作手机号的归属地信息
    PhoneLocationInfoServiceImpl phoneLocationInfoServiceImpl;

    @Resource
    // 用于操作归属地缓存
    private RedisUtil redisUtil;

    // 声明日志记录器
    private static final Logger logger = LoggerFactory.getLogger(PhoneLocationInfoController.class);

    /**
     * 查询归属地信息
     *
     * @param phoneNumber 电话号码
     * @return 包含归属地信息的响应对象
     */
    @GetMapping("/getLocate")
    public BaseResponse<PhoneLocationInfo> getLocation(@RequestParam("phoneNumber") String phoneNumber) {
        logger.info("Phone number: {}", phoneNumber);
        // 参数校验
        int code = phoneLocationInfoServiceImpl.validPhoneNumber(phoneNumber);
        if (code != 0) {
            return ResultUtil.error(ErrorCode.getByCode(code));
        }

        // 先从Redis缓存中查找数据
        PhoneLocationInfo phoneLocationInfo = (PhoneLocationInfo) redisUtil.get(phoneNumber);
        if (phoneLocationInfo != null) {
            // 如果缓存中有数据，直接返回
            return ResultUtil.success(phoneLocationInfo);
        }

        // 如果缓存中没有数据，进行供应商查询，并将查询结果存入缓存
        phoneLocationInfo = new PhoneLocationInfo();
        phoneLocationInfo.setPhoneNumber(phoneNumber);

        // 获取手机号的归属地信息
        String locate =  phoneLocationInfoServiceImpl.getLocation(phoneNumber);
        if (locate != null) {
            // 设置手机号的归属地信息
            phoneLocationInfo.setProvince(locate.split(" ")[0]);
            phoneLocationInfo.setCity(locate.split(" ")[1]);
            logger.info("Phone location: {}", phoneLocationInfo.getProvince() + " " + phoneLocationInfo.getCity());
            phoneLocationInfoServiceImpl.cachePhoneLocationInfo(phoneLocationInfo);
            return ResultUtil.success(phoneLocationInfo);
        }
        return ResultUtil.error(ErrorCode.LOCATION_NOT_FOUND);
    }
}
