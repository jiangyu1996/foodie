package cn.decentchina.exception;

import cn.decentchina.enums.ErrorCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jiangyu
 * @date 2020/1/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorCodeException extends RuntimeException {

    private static final long serialVersionUID = -7638041501183925225L;

    private Integer code;

    public ErrorCodeException(String message) {
        super(message);
        this.code = ErrorCodeEnum.NO.getCode();
    }

    public ErrorCodeException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public ErrorCodeException(ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}
