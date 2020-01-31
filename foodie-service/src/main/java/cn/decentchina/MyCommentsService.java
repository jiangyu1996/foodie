package cn.decentchina;

import cn.decentchina.pojo.OrderItems;

import java.util.List;

/**
 * @author jiangyu
 * @date 2020/1/31
 */
public interface MyCommentsService {
    /**
     * 根据订单id查询关联的商品
     *
     * @param orderId 订单id
     * @return 订单商品
     */
    List<OrderItems> queryPendingComment(String orderId);
}
