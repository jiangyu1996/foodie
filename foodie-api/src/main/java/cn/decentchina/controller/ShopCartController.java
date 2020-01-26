package cn.decentchina.controller;

import cn.decentchina.bo.ShopcartBO;
import cn.decentchina.entity.SimpleMessage;
import cn.decentchina.enums.ErrorCodeEnum;
import cn.decentchina.exception.ErrorCodeException;
import cn.decentchina.impl.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jiangyu
 * @date 2020/1/26
 */
@RestController
@RequestMapping("shopcart")
public class ShopCartController {

    @Autowired
    private ItemService itemService;

    @PostMapping("add")
    public SimpleMessage add(@RequestParam String userId, @RequestBody ShopcartBO shopcartBO,
                             HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(userId)) {
            throw new ErrorCodeException(ErrorCodeEnum.NO.getCode(), "未登录");
        }
        System.out.println(shopcartBO);
        return new SimpleMessage();
    }

    /**
     * 用户长时间未登录购物车，同步刷新价格
     *
     * @param userId     用户id
     * @param itemSpecId 产品id
     * @return 产品价格信息
     */
    @PostMapping("del")
    public SimpleMessage del(@RequestParam String userId, @RequestParam String itemSpecId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            throw new ErrorCodeException(ErrorCodeEnum.INVALID_PARAMS);
        }
        return new SimpleMessage();
    }
}
