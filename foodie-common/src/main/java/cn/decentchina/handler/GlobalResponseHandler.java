package cn.decentchina.handler;

import cn.decentchina.entity.MessageBean;
import cn.decentchina.entity.SimpleMessage;
import cn.decentchina.enums.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author jiangy
 * @date 2020/1/19
 */
@Slf4j
@ControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) {
            return null;
        }
        if (body instanceof String || body instanceof MessageBean || body instanceof SimpleMessage) {
            log.info("[{}]接口响应[{}]", request.getURI(), body);
            return body;
        }
        MessageBean result = new MessageBean();
        result.setErrorCode(ErrorCodeEnum.OK.getCode());
        result.setErrorMsg("OK");
        result.setData(body);
        log.info("[{}]接口响应[{}]", request.getURI(), StringUtils.left(body.toString(), 200));
        body = result;
        return body;
    }
}
