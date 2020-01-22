package cn.decentchina.controller;

import cn.decentchina.UserService;
import cn.decentchina.bo.UserBO;
import cn.decentchina.entity.SimpleMessage;
import cn.decentchina.enums.ErrorCodeEnum;
import cn.decentchina.exception.ErrorCodeException;
import cn.decentchina.pojo.User;
import cn.decentchina.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jiangyu
 * @date 2020/1/18
 */
@RequestMapping("passPort")
public class PassPortController {

    private final static int MAX_PWD_LEN = 6;
    @Autowired
    private UserService userService;

    @PostMapping("checkUserExists")
    public SimpleMessage checkUserExists(@RequestParam String userName) {
        if (StringUtils.isBlank(userName)) {
            throw new ErrorCodeException("用户名不能为空");
        }
        User user = userService.queryByName(userName);
        if (user == null) {
            throw new ErrorCodeException("用户已经存在");
        }
        return new SimpleMessage();
    }

    @PostMapping("createUser")
    public Object createUser(@RequestBody UserBO userBO) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();
        // 0. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPwd)) {
            throw new ErrorCodeException(ErrorCodeEnum.INVALID_USER);
        }
        // 1. 查询用户名是否存在
        checkUserExists(username);
        // 2. 密码长度不能少于6位
        if (password.length() < MAX_PWD_LEN) {
            throw new ErrorCodeException("密码长度不能少于6");
        }
        // 3. 判断两次密码是否一致
        if (!password.equals(confirmPwd)) {
            throw new ErrorCodeException("两次密码输入不一致");
        }
        User user = userService.createUser(userBO);
        return new SimpleMessage(user);
    }

    @PostMapping("/login")
    public SimpleMessage login(@RequestBody UserBO userBO,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {

        String username = userBO.getUsername();
        String password = userBO.getPassword();

        // 0. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            throw new ErrorCodeException("用户名或密码不能为空");
        }

        // 1. 实现登录
        User user = userService.queryByName(username);
        if (user == null) {
            throw new ErrorCodeException("用户已经存在");
        }
        if (!StringUtils.equals(MD5Utils.getMD5Str(password), user.getPassword())) {
            throw new ErrorCodeException("用户名或密码不正确");
        }
        return new SimpleMessage();
    }


}
