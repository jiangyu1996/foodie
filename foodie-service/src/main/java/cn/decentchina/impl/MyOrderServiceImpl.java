package cn.decentchina.impl;

import cn.decentchina.MyOrderService;
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
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author jiangyu
 * @date 2020/1/31
 */
@Service
public class MyOrderServiceImpl implements MyOrderService {
    @Autowired
    private OrderCustomMapper orderCustomMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;

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

    private PagedGridResult getPagedGridResult(Integer page, List<?> list) {
        PagedGridResult result = new PagedGridResult();
        PageInfo pageInfo = new PageInfo<>(list);
        result.setPage(page);
        result.setRows(pageInfo.getList());
        result.setRecords(pageInfo.getTotal());
        result.setTotal(pageInfo.getPages());
        return result;
    }
}
