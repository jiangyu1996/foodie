package cn.decentchina.handler;

import cn.decentchina.entity.SimpleMessage;
import cn.decentchina.enums.ErrorCodeEnum;
import cn.decentchina.exception.ErrorCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jiangyu
 * @date 2020/1/18
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public SimpleMessage handleAndReturnData(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        SimpleMessage message = new SimpleMessage();
        if (ex instanceof ErrorCodeException) {
            ErrorCodeException e = (ErrorCodeException) ex;
            log.warn("[{}]接口异常[{}]", request.getRequestURI(), e.getMessage());
            message.setStatus(e.getCode());
            message.setMsg(e.getMessage());
            return message;
        }
        log.error("[{}]系统异常", request.getRequestURI(), ex);
        message.setStatus(ErrorCodeEnum.ERROR.getCode());
        message.setMsg(ErrorCodeEnum.ERROR.getMessage());
        return message;
    }
}
