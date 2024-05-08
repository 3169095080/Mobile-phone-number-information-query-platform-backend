package org.example.phonelocatebackend.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 标记响应类
 *
 */
@Data
public class PhoneMarkerInfoResponse implements Serializable {

    /**
     * 状态码
     */
    private int code;

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

    private static final long serialVersionUID = 1L;
}
