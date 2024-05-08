package org.example.phonelocatebackend.controller;

import org.example.phonelocatebackend.exception.BusinessException;
import org.example.phonelocatebackend.model.dto.PhoneMarkerInfoResponse;
import org.example.phonelocatebackend.model.dto.PhoneUpdateMarkerInfoRequest;
import org.example.phonelocatebackend.model.entity.PhoneMarkerInfo;
import org.example.phonelocatebackend.service.impl.PhoneMarkerInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 手机号标记信息控制器
 */
@CrossOrigin
@RestController
@RequestMapping("/phone")
public class PhoneMarkerInfoController {
    @Resource
    // 用于操作手机号的被标注信息
    PhoneMarkerInfoServiceImpl phoneMarkerInfoServiceImpl;

    // 声明日志记录器
    private static final Logger logger = LoggerFactory.getLogger(PhoneMarkerInfoController.class);

    /**
     * 获取手机号的被标记信息
     * @param phoneNumber 手机号码
     * @return 包含被标记信息的响应对象
     */
    @GetMapping("/getMarkerInfo")
    public PhoneMarkerInfoResponse getMarkerInfo(@RequestParam("phoneNumber") String phoneNumber) {
        logger.info("phoneNum:{}", phoneNumber);
        // 创建响应对象
        PhoneMarkerInfoResponse response = new PhoneMarkerInfoResponse();
        try {
            // 根据手机号获取HarassmentCount、FraudCount、AdvertisementCount
            response.setHarassmentCount(phoneMarkerInfoServiceImpl.getHarassmentCountByPhoneNumber(phoneNumber));
            response.setFraudCount(phoneMarkerInfoServiceImpl.getFraudCountByPhoneNumber(phoneNumber));
            response.setAdvertisementCount(phoneMarkerInfoServiceImpl.getAdvertisementCountByPhoneNumber(phoneNumber));
            response.setCode(0);

            logger.info("Response:{}", response);
        } catch (BusinessException e) {
            // 捕获异常并记录日志
            logger.error("Error occurred while getting marker info for phone number {}: {}", phoneNumber, e.getMessage());
        }
        // 返回响应对象
        return response;
    }

    /**
     * 更新手机号的被标记信息
     * @param request 包含标记信息的请求对象
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/updateMarkerInfo")
    public synchronized void updateMarkerInfo(@RequestBody PhoneUpdateMarkerInfoRequest request) {
        logger.info("Request:{}", request);
        try {
            PhoneMarkerInfo phoneMarkerInfo = phoneMarkerInfoServiceImpl.getByPhoneNumber(request.getPhoneNumber());
            logger.info("phoneMarkerInfo:{}", phoneMarkerInfo);

            if (phoneMarkerInfo == null) {
                // 如果数据库中不存在该手机号的记录，则创建一个新的 PhoneMarkerInfo 对象并保存到数据库
                phoneMarkerInfo = new PhoneMarkerInfo(request.getPhoneNumber());
            }

            // 更新标记信息
            phoneMarkerInfo.updatePhoneMarkerInfo(request.getMarkType());

            // 保存或更新到数据库
            phoneMarkerInfoServiceImpl.saveOrUpdate(phoneMarkerInfo);

            logger.info("UpdatedPhoneMarkerInfo:{}", phoneMarkerInfoServiceImpl.getByPhoneNumber(request.getPhoneNumber()));
        } catch (BusinessException e) {
            // 捕获异常并记录日志
            logger.error("Error occurred while updating marker info for phone number {}: {}", request.getPhoneNumber(), e.getMessage());
            // 抛出异常以触发事务回滚
            throw e;
        }
    }
}