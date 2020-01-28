package cn.decentchina.impl;

import cn.decentchina.ItemService;
import cn.decentchina.OrderService;
import cn.decentchina.UserAddressService;
import cn.decentchina.bo.SubmitOrderBO;
import cn.decentchina.enums.OrderStatusEnum;
import cn.decentchina.enums.YesOrNo;
import cn.decentchina.mapper.OrderItemsMapper;
import cn.decentchina.mapper.OrderStatusMapper;
import cn.decentchina.mapper.OrdersMapper;
import cn.decentchina.pojo.*;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author jiangyu
 * @date 2020/1/28
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private Sid sid;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createOrder(SubmitOrderBO submitOrderBO) {
        UserAddress address = userAddressService.queryUserAddress(submitOrderBO.getUserId(), submitOrderBO.getAddressId());
        Orders orders = new Orders();
        BeanUtils.copyProperties(submitOrderBO, orders);
        orders.setId(sid.nextShort());
        orders.setReceiverName(address.getReceiver());
        orders.setReceiverMobile(address.getMobile());
        orders.setReceiverAddress(address.getProvince() + address.getCity() + address.getDistrict() + address.getDetail());
        orders.setPostAmount(0);
        orders.setIsComment(YesOrNo.NO.type);
        orders.setIsDelete(YesOrNo.NO.type);
        orders.setCreatedTime(new Date());
        orders.setUpdatedTime(new Date());
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        String[] itemSpecIdArr = itemSpecIds.split(",");
        int totalAmount = 0, realPayAmount = 0;
        String orderId = sid.nextShort();
        for (String itemSpecId : itemSpecIdArr) {
            ItemsSpec itemSpec = itemService.querySpecById(itemSpecId);
            int buyCounts = 1;
            totalAmount += itemSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemSpec.getPriceDiscount() * buyCounts;
            String itemId = itemSpec.getItemId();
            Items item = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemMainImgById(itemId);

            String subOrderId = sid.nextShort();
            OrderItems subOrderItem = new OrderItems();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemImg(imgUrl);
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemSpecName(itemSpec.getName());
            subOrderItem.setPrice(itemSpec.getPriceDiscount());
            orderItemsMapper.insert(subOrderItem);
            // 扣除库存
            itemService.decreaseItemSpecStock(itemSpecId, buyCounts);

        }

        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPayAmount);
        ordersMapper.insert(orders);

        // 3. 保存订单状态表
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);
        return orderId;
    }
}
