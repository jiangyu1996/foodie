package cn.decentchina.enums;

import lombok.Getter;

/**
 * @author jiangy
 */
@Getter
public enum PayMethodEnum {

    /**
     * 微信
     */
    WEI_XIN(1, "微信"),
    /**
     * 支付宝
     */
    ALI_PAY(2, "支付宝");

    private final Integer type;
    private final String value;

    PayMethodEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

}
