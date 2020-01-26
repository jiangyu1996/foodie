package cn.decentchina.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 全局http错误编码
 *
 * @author jiangy
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    /**
     * 泛用错误码
     */
    OK(200, "请求通过"),
    NO(500, "请求不通过"),
    INVALID_USER(1000, "用户名或密码有误"),
    INVALID_PARAMS(1001, "参数有误"),
    ERROR(9999, "系统异常"),
    ;

    private int code;

    private String message;


}
