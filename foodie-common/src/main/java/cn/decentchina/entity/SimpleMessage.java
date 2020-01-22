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
    private Integer status;
    private String msg;
    private Object data;

    public SimpleMessage() {
        this.status = ErrorCodeEnum.OK.getCode();
        this.msg = ErrorCodeEnum.OK.getMessage();
    }

    public SimpleMessage(Object data) {
        this.status = ErrorCodeEnum.OK.getCode();
        this.msg = ErrorCodeEnum.OK.getMessage();
        this.data = data;
    }

    public SimpleMessage(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public SimpleMessage(ErrorCodeEnum status) {
        this.status = status.getCode();
        this.msg = status.getMessage();
    }

}
