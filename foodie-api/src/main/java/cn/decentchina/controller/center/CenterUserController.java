package cn.decentchina.controller.center;

import cn.decentchina.CenterUserService;
import cn.decentchina.CommonConstant;
import cn.decentchina.bo.center.CenterUserBO;
import cn.decentchina.entity.SimpleMessage;
import cn.decentchina.exception.ErrorCodeException;
import cn.decentchina.pojo.User;
import cn.decentchina.utils.CookieUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * @author jiangyu
 * @date 2020/1/30
 */
@RestController
@RequestMapping("userInfo")
public class CenterUserController {

    @Autowired
    private CenterUserService centerUserService;

    @PostMapping("update")
    public SimpleMessage update(@RequestParam String userId, @Valid @RequestBody CenterUserBO centerUserBO,
                                BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            String errorInfo = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(","));
            throw new ErrorCodeException(errorInfo);
        }
        User user = centerUserService.update(userId, centerUserBO);
        setNullProperty(user);
        CookieUtils.setCookie(request, response, CommonConstant.USER, JSONObject.toJSONString(user), true);
        return new SimpleMessage(user);
    }

    private void setNullProperty(User user) {
        user.setPassword(null);
        user.setRealname(null);
        user.setBirthday(null);
        user.setEmail(null);
        user.setCreatedTime(null);
        user.setUpdatedTime(null);
    }
}
