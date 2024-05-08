package org.example.phonelocatebackend.controller;

import org.example.phonelocatebackend.model.dto.BaseResponse;
import org.example.phonelocatebackend.model.entity.PhoneOperatorInfo;
import org.example.phonelocatebackend.model.enums.ErrorCode;
import org.example.phonelocatebackend.model.enums.PhoneOperatorEnum;
import org.example.phonelocatebackend.service.impl.PhoneOperatorInfoServiceImpl;
import org.example.phonelocatebackend.utils.RedisUtil;
import org.example.phonelocatebackend.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 手机号运营商信息控制器
 */
@CrossOrigin
@RestController
@RequestMapping("/phone")
public class PhoneOperatorInfoController {

    @Resource
    // 用于操作手机号的供应商信息
    PhoneOperatorInfoServiceImpl phoneOperatorInfoServiceImpl;

    @Resource
    // 用于操作供应商缓存
    private RedisUtil redisUtil;

    // 声明日志记录器
    private static final Logger logger = LoggerFactory.getLogger(PhoneOperatorInfoController.class);

    // 用于判断输入框内电话号有没有达到3位数
//    private static boolean isPhoneTopThreeNum = false;

    /**
     * 查询供应商信息
     *
     * @param phoneTopThreeNumber 电话号前三位数字
     * @return 包含供应商信息的响应对象
     */
    @GetMapping("/getOperator")
    public BaseResponse<String> getOperator(@RequestParam("phoneTopThreeNumber") String phoneTopThreeNumber) {

        logger.info("Phone top three number: {}",  phoneTopThreeNumber);
        phoneTopThreeNumber = phoneTopThreeNumber.substring(0, 3);

        // 参数校验
        int code = phoneOperatorInfoServiceImpl.validPhoneTopThreeNumber(phoneTopThreeNumber);
        if (code != 0) {
            return ResultUtil.error(ErrorCode.getByCode(code));
        }
        // 先从Redis缓存中查找数据
        PhoneOperatorInfo phoneOperatorInfo = (PhoneOperatorInfo) redisUtil.get(phoneTopThreeNumber);
        if (phoneOperatorInfo != null) {
            // 如果缓存中有数据，直接返回
            PhoneOperatorEnum operator = phoneOperatorInfo.getOperator();
            return ResultUtil.success(operator.getName());
        }
        // 如果缓存中没有数据，进行供应商查询，并将查询结果存入缓存
        phoneOperatorInfo = new PhoneOperatorInfo();
        phoneOperatorInfo.setPhoneTopThreeNumber(phoneTopThreeNumber);

        // 获取手机号的营业厅信息
        PhoneOperatorEnum operator =  phoneOperatorInfoServiceImpl.getOperator(phoneOperatorInfo.getPhoneTopThreeNumber());
        if (operator != null) {
            // 设置手机号的营业厅信息
            phoneOperatorInfo.setOperator(operator);
            logger.info("Phone operator: {}",operator.getName());
            phoneOperatorInfoServiceImpl.cachePhoneOperatorInfo(phoneOperatorInfo);
            return ResultUtil.success(operator.getName());
        }
        return ResultUtil.error(ErrorCode.UNKNOWN_OPERATOR);
    }
}
