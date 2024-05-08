package org.example.phonelocatebackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 手机号标注信息
 * @TableName phone_marker_info
 */
@TableName(value ="phone_marker_info")
@Data
public class PhoneMarkerInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 被标记为骚扰电话的次数
     */
    private Long harassmentCount;

    /**
     * 被标记为诈骗电话的次数
     */
    private Long fraudCount;

    /**
     * 被标记为广告推销的次数
     */
    private Long advertisementCount;

    /**
     * 创建时间
     */
    private Date createTime;

    public PhoneMarkerInfo() {
    }

    public PhoneMarkerInfo(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.harassmentCount = 0L;
        this.fraudCount = 0L;
        this.advertisementCount = 0L;
        this.createTime = new Date();
    }

    public PhoneMarkerInfo(Long id, String phoneNumber, Long harassmentCount, Long fraudCount, Long advertisementCount, Date createTime) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.harassmentCount = harassmentCount;
        this.fraudCount = fraudCount;
        this.advertisementCount = advertisementCount;
        this.createTime = createTime;
    }

    /**
     * 根据标记类型更新标记信息
     * @param markType
     */
    public void updatePhoneMarkerInfo(String markType) {
        if (markType.equals("0")) {
            this.harassmentCount++;
        } else if (markType.equals("1")) {
            this.fraudCount++;
        } else {
            this.advertisementCount++;
        }
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
