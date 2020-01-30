package cn.decentchina.controller.center;

import cn.decentchina.CenterUserService;
import cn.decentchina.entity.SimpleMessage;
import cn.decentchina.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiangyu
 * @date 2020/1/30
 */
@RestController
@RequestMapping("center")
public class CenterController {

    @Autowired
    private CenterUserService centerUserService;

    @GetMapping("userInfo")
    public SimpleMessage userInfo(@RequestParam String userId) {
        User user = centerUserService.queryUserInfo(userId);
        return new SimpleMessage(user);
    }
}
