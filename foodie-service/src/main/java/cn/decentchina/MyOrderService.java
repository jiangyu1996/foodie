package cn.decentchina;

import cn.decentchina.pojo.Orders;
import cn.decentchina.utils.PagedGridResult;
import cn.decentchina.vo.OrderStatusCountsVO;

/**
 * @author jiangyu
 * @date 2020/1/31
 */
public interface MyOrderService {

    /**
     * 查询订单
     *
     * @param userId      用户id
     * @param orderStatus 订单状态
     * @param page        查询页数
     * @param pageSize    每页条数
     * @return 分页订单信息
     */
    PagedGridResult listOrder(String userId, Integer orderStatus, Integer page, Integer pageSize);

    /**
     * 查询订单
     *
     * @param userId  用户id
     * @param orderId 订单id
     * @return 订单信息
     */
    Orders queryMyOrder(String userId, String orderId);

    /**
     * 确认收货哦
     *
     * @param orderId 订单id
     */
    void updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单(非物理逻辑删除)
     *
     * @param userId  用户id
     * @param orderId 订单id
     */
    void deleteOrder(String userId, String orderId);

    /**
     * 查询订单数量
     *
     * @param userId 用户id
     * @return 数量信息
     */
    OrderStatusCountsVO getOrderStatusCounts(String userId);

    /**
     * 查询订单动向
     *
     * @param userId   用户id
     * @param page     查询页数
     * @param pageSize 每页条数
     * @return 订单动向信息
     */
    PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize);
}
