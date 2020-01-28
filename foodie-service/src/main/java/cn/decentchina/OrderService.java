package cn.decentchina;

import cn.decentchina.bo.SubmitOrderBO;

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
    String createOrder(SubmitOrderBO submitOrderBO);
}
