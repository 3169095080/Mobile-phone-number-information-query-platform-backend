package org.example.phonelocatebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.phonelocatebackend.model.entity.PhoneMarkerInfo;

/**
* @author Administrator
* @description 针对表【phone_marker_info(手机号标注信息)】的数据库操作Mapper
* @createDate 2024-04-30 10:26:23
* @Entity generator.domain.PhoneMarkerInfo
*/
@Mapper
public interface PhoneMarkerInfoMapper extends BaseMapper<PhoneMarkerInfo> {
    /**
     * 根据手机号获取手机号HarassmentCount
     * @param phoneNumber 手机号
     * @return 被标记为骚扰电话的次数
     */
    Long getHarassmentCountByPhoneNumber(String phoneNumber);

    /**
     * 根据手机号获取手机号 FraudCount
     * @param phoneNumber 手机号
     * @return 被标记为诈骗电话的次数
     */
    Long getFraudCountByPhoneNumber(String phoneNumber);

    /**
     * 根据手机号获取手机号 AdvertisementCount
     * @param phoneNumber 手机号
     * @return 被标记为广告推销的次数
     */
    Long getAdvertisementCountByPhoneNumber(String phoneNumber);

    /**
     * 根据手机号获取手机号标注信息
     * @param phoneNumber 手机号
     * @return 手机号标记信息对象
     */
    PhoneMarkerInfo getByPhoneNumber(String phoneNumber);
}




