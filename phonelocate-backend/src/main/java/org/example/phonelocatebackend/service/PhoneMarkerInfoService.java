package org.example.phonelocatebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.phonelocatebackend.model.entity.PhoneMarkerInfo;

/**
* @author Administrator
* @description 针对表【phone_marker_info(手机号标注信息)】的数据库操作Service
* @createDate 2024-04-30 10:26:23
*/
public interface PhoneMarkerInfoService extends IService<PhoneMarkerInfo> {

    /**
     * 获取手机号被标记信息
     * @param phoneNumber 手机号
     * @return 手机号被标记信息对象
     */
    PhoneMarkerInfo getByPhoneNumber(String phoneNumber);
}
