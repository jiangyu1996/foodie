package cn.decentchina.impl;

import cn.decentchina.MyOrderService;
import cn.decentchina.base.BaseService;
import cn.decentchina.enums.OrderStatusEnum;
import cn.decentchina.enums.YesOrNo;
import cn.decentchina.exception.ErrorCodeException;
import cn.decentchina.mapper.OrderCustomMapper;
import cn.decentchina.mapper.OrderStatusMapper;
import cn.decentchina.mapper.OrdersMapper;
import cn.decentchina.pojo.OrderStatus;
import cn.decentchina.pojo.Orders;
import cn.decentchina.utils.PagedGridResult;
import cn.decentchina.vo.ItemCommentVO;
import cn.decentchina.vo.OrderStatusCountsVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangyu
 * @date 2020/1/31
 */
@Service
public class MyOrderServiceImpl extends BaseService implements MyOrderService {
    @Autowired
    private OrderCustomMapper orderCustomMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Override
    public PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        List<OrderStatus> list = orderCustomMapper.getMyOrderTrend(map);
        return getPagedGridResult(page, list);
    }

    @Override
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("orderStatus", OrderStatusEnum.WAIT_PAY.type);
        int waitPayCounts = orderCustomMapper.getMyOrderStatusCounts(map);
        map.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        int waitDeliverCounts = orderCustomMapper.getMyOrderStatusCounts(map);
        map.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int waitReceiveCounts = orderCustomMapper.getMyOrderStatusCounts(map);
        map.put("orderStatus", OrderStatusEnum.SUCCESS.type);
        map.put("isComment", YesOrNo.NO.type);
        int waitCommentCounts = orderCustomMapper.getMyOrderStatusCounts(map);
        return new OrderStatusCountsVO(waitPayCounts, waitDeliverCounts, waitReceiveCounts, waitCommentCounts);
    }

    @Override
    public void deleteOrder(String userId, String orderId) {

        Orders updateOrder = new Orders();
        updateOrder.setIsDelete(YesOrNo.YES.type);
        updateOrder.setUpdatedTime(new Date());
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", orderId);
        criteria.andEqualTo("userId", userId);
        int row = ordersMapper.updateByExampleSelective(updateOrder, example);
        if (row != 1) {
            throw new ErrorCodeException("订单确认收货失败！");
        }
    }

    @Override
    public void updateReceiveOrderStatus(String orderId) {
        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        updateOrder.setSuccessTime(new Date());
        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int row = orderStatusMapper.updateByExampleSelective(updateOrder, example);
        if (row != 1) {
            throw new ErrorCodeException("订单确认收货失败！");
        }
    }

    @Override
    public Orders queryMyOrder(String userId, String orderId) {
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setId(orderId);
        orders.setIsDelete(YesOrNo.NO.type);
        return ordersMapper.selectOne(orders);
    }

    @Override
    public PagedGridResult listOrder(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }
        PageHelper.startPage(page, pageSize);
        List<ItemCommentVO> list = orderCustomMapper.queryMyOrders(map);
        return getPagedGridResult(page, list);
    }

}
