package org.example.phonelocatebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.phonelocatebackend.mapper.PhoneMarkerInfoMapper;
import org.example.phonelocatebackend.model.entity.PhoneMarkerInfo;
import org.example.phonelocatebackend.service.PhoneMarkerInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【phone_marker_info(手机号标注信息)】的数据库操作Service实现
* @createDate 2024-04-30 10:26:23
*/
@Service
public class PhoneMarkerInfoServiceImpl extends ServiceImpl<PhoneMarkerInfoMapper, PhoneMarkerInfo>
    implements PhoneMarkerInfoService {

    @Resource
    // 用于操作标注信息
    PhoneMarkerInfoMapper phoneMarkerInfoMapper;

    /**
     * 获取手机号被标记为骚扰电话的次数
     * @param phoneNum 手机号
     * @return 手机号被标记为骚扰电话的次数
     */
    public Long getHarassmentCountByPhoneNumber(String phoneNum) {
        return phoneMarkerInfoMapper.getHarassmentCountByPhoneNumber(phoneNum);
    }

    /**
     * 获取手机号被标记为诈骗电话的次数
     * @param phoneNum 手机号
     * @return 手机号被标记为诈骗电话的次数
     */
    public Long getFraudCountByPhoneNumber(String phoneNum) {
        return phoneMarkerInfoMapper.getFraudCountByPhoneNumber(phoneNum);
    }

    /**
     * 获取手机号被标记为广告推销的次数
     * @param phoneNum 手机号
     * @return 手机号被标记为广告推销的次数
     */
    public Long getAdvertisementCountByPhoneNumber(String phoneNum) {
        return phoneMarkerInfoMapper.getAdvertisementCountByPhoneNumber(phoneNum);
    }

    /**
     * 获取手机号被标记信息
     * @param phoneNumber 手机号
     * @return 手机号被标记信息对象
     */
    @Override
    public PhoneMarkerInfo getByPhoneNumber(String phoneNumber) {
        return phoneMarkerInfoMapper.getByPhoneNumber(phoneNumber);
    }
}




