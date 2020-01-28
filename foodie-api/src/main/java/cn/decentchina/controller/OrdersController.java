package cn.decentchina.controller;

import cn.decentchina.OrderService;
import cn.decentchina.bo.SubmitOrderBO;
import cn.decentchina.entity.SimpleMessage;
import cn.decentchina.enums.ErrorCodeEnum;
import cn.decentchina.enums.PayMethodEnum;
import cn.decentchina.exception.ErrorCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiangyu
 * @date 2020/1/27
 */
@RestController
@RequestMapping("orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @PostMapping("create")
    public SimpleMessage create(@RequestBody SubmitOrderBO submitOrderBO) {
        System.out.println(submitOrderBO);
        if (!PayMethodEnum.WEI_XIN.getType().equals(submitOrderBO.getPayMethod()) && !PayMethodEnum.ALI_PAY.getType().equals(submitOrderBO.getPayMethod())) {
            throw new ErrorCodeException(ErrorCodeEnum.NO.getCode(), "不支持其他支付方式");

        }
        String orderId = orderService.createOrder(submitOrderBO);
        return new SimpleMessage(orderId);

    }
}
