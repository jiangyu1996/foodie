package cn.decentchina.controller;

import cn.decentchina.OrderService;
import cn.decentchina.bo.SubmitOrderBO;
import cn.decentchina.entity.SimpleMessage;
import cn.decentchina.enums.ErrorCodeEnum;
import cn.decentchina.enums.OrderStatusEnum;
import cn.decentchina.enums.PayMethodEnum;
import cn.decentchina.exception.ErrorCodeException;
import cn.decentchina.vo.MerchantOrdersVO;
import cn.decentchina.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author jiangyu
 * @date 2020/1/27
 */
@Slf4j
@RestController
@RequestMapping("orders")
public class OrdersController {

    /**
     * 回调通知地址
     */
    private final static String PAY_RETURN_URL = "http://192.168.3.26:8088/orders/notifyMerchantOrderPaid";
    /**
     * 支付中心的调用地址
     */
    private final static String PAYMENT_URL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
    @Autowired
    private OrderService orderService;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("create")
    public SimpleMessage create(@RequestBody SubmitOrderBO submitOrderBO) {
        System.out.println(submitOrderBO);
        if (!PayMethodEnum.WEI_XIN.getType().equals(submitOrderBO.getPayMethod()) && !PayMethodEnum.ALI_PAY.getType().equals(submitOrderBO.getPayMethod())) {
            throw new ErrorCodeException(ErrorCodeEnum.NO.getCode(), "不支持其他支付方式");

        }
        OrderVO order = orderService.createOrder(submitOrderBO);
        MerchantOrdersVO merchantOrdersVO = order.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(PAY_RETURN_URL);
        merchantOrdersVO.setAmount(1);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("imoocUserId", "imooc");
        httpHeaders.add("password", "imooc");
        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, httpHeaders);
        ResponseEntity<SimpleMessage> responseEntity = restTemplate.postForEntity(PAYMENT_URL, entity, SimpleMessage.class);
        SimpleMessage body = responseEntity.getBody();
        if (body == null || body.getStatus() != ErrorCodeEnum.OK.getCode()) {
            log.error("发送错误：{}", body.getMsg());
            throw new ErrorCodeException("支付中心订单创建失败，请联系管理员！");
        }
        return new SimpleMessage(order.getOrderId());
    }

    @PostMapping("notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }
}
