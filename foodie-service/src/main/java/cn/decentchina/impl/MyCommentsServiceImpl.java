package cn.decentchina.impl;

import cn.decentchina.MyCommentsService;
import cn.decentchina.base.BaseService;
import cn.decentchina.bo.center.OrderItemsCommentBO;
import cn.decentchina.enums.YesOrNo;
import cn.decentchina.mapper.ItemsCommentsCustomMapper;
import cn.decentchina.mapper.OrderItemsMapper;
import cn.decentchina.mapper.OrderStatusMapper;
import cn.decentchina.mapper.OrdersMapper;
import cn.decentchina.pojo.OrderItems;
import cn.decentchina.pojo.OrderStatus;
import cn.decentchina.pojo.Orders;
import cn.decentchina.utils.PagedGridResult;
import cn.decentchina.vo.MyCommentVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangyu
 * @date 2020/1/31
 */
@Service
public class MyCommentsServiceImpl extends BaseService implements MyCommentsService {
    @Autowired
    private Sid sid;
    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private ItemsCommentsCustomMapper itemsCommentsCustomMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Override
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> list = itemsCommentsCustomMapper.queryMyComments(map);
        return getPagedGridResult(page, list);
    }


    @Override
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList) {
        // 1. 保存评价 items_comments
        for (OrderItemsCommentBO oic : commentList) {
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsCustomMapper.saveComments(map);
        // 2. 修改订单表改已评价 orders
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(order);
        // 3. 修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems query = new OrderItems();
        query.setOrderId(orderId);
        return orderItemsMapper.select(query);

    }
}
