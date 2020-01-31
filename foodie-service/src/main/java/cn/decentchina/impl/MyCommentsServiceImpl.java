package cn.decentchina.impl;

import cn.decentchina.MyCommentsService;
import cn.decentchina.mapper.OrderItemsMapper;
import cn.decentchina.pojo.OrderItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jiangyu
 * @date 2020/1/31
 */
@Service
public class MyCommentsServiceImpl implements MyCommentsService {
    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems query = new OrderItems();
        query.setOrderId(orderId);
        return orderItemsMapper.select(query);

    }
}
