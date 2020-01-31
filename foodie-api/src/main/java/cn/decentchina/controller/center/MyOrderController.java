package cn.decentchina.controller.center;

import cn.decentchina.MyOrderService;
import cn.decentchina.entity.SimpleMessage;
import cn.decentchina.exception.ErrorCodeException;
import cn.decentchina.pojo.Orders;
import cn.decentchina.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiangyu
 * @date 2020/1/31
 */
@RestController
@RequestMapping("myorders")
public class MyOrderController {

    @Autowired
    private MyOrderService myOrderService;

    @PostMapping("query")
    public SimpleMessage query(String userId, Integer orderStatus, @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "20") Integer pageSize) {
        PagedGridResult result = myOrderService.listOrder(userId, orderStatus, page, pageSize);
        return new SimpleMessage(result);
    }

    @PostMapping("/confirmReceive")
    public SimpleMessage confirmReceive(@RequestParam String orderId, @RequestParam String userId) {
        checkUserOrder(userId, orderId);
        myOrderService.updateReceiveOrderStatus(orderId);
        return new SimpleMessage();
    }

    @PostMapping("/delete")
    public SimpleMessage delete(@RequestParam String orderId, @RequestParam String userId) {
        checkUserOrder(userId, orderId);
        myOrderService.deleteOrder(userId, orderId);
        return new SimpleMessage();
    }

    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     */
    private void checkUserOrder(String userId, String orderId) {
        Orders order = myOrderService.queryMyOrder(userId, orderId);
        if (order == null) {
            throw new ErrorCodeException("订单不存在！");
        }
    }
}
