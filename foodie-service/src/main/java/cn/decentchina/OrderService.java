package cn.decentchina;

import cn.decentchina.bo.SubmitOrderBO;
import cn.decentchina.vo.OrderVO;

/**
 * @author jiangyu
 * @date 2020/1/28
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param submitOrderBO 订单信息
     * @return 订单id
     */
    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     *
     * @param merchantOrderId 商户订单id
     * @param type            订单状态
     */
    void updateOrderStatus(String merchantOrderId, Integer type);
}
