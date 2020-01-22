package cn.decentchina.entity;

import cn.decentchina.enums.ErrorCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口简易返回信息
 *
 * @author wangyx
 */
@Data
public class SimpleMessage implements Serializable {
    private static final long serialVersionUID = -2957516153008725933L;
    private Integer errorCode;
    private String errorMsg;
    private Object data;

    public SimpleMessage() {
        this.errorCode = ErrorCodeEnum.OK.getCode();
        this.errorMsg = ErrorCodeEnum.OK.getMessage();
    }

    public SimpleMessage(Object data) {
        this.errorCode = ErrorCodeEnum.OK.getCode();
        this.errorMsg = ErrorCodeEnum.OK.getMessage();
        this.data = data;
    }

    public SimpleMessage(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public SimpleMessage(ErrorCodeEnum errorCode) {
        this.errorCode = errorCode.getCode();
        this.errorMsg = errorCode.getMessage();
    }

}
