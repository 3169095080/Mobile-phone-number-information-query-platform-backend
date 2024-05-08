package org.example.phonelocatebackend.model.enums;

/**
 * 手机运营商枚举
 */
public enum PhoneOperatorEnum {

    /**
     * 代表电信运营商
     */
    TELECOM("电信"),
    /**
     * 代表移动运营商
     */
    MOBILE("移动"),
    /**
     * 代表联通运营商
     */
    UNICOS("联通");

    /**
     * 运营商名称
     */
    private final String name;

    /**
     * 构造函数
     * @param name 运营商名称
     */
    PhoneOperatorEnum(String name) {
        this.name = name;
    }

    /**
     * 获取运营商名称
     * @return 运营商名称
     */
    public String getName() {
        return name;
    }
}
