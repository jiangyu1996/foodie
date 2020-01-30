package cn.decentchina.impl;

import cn.decentchina.ItemService;
import cn.decentchina.OrderService;
import cn.decentchina.UserAddressService;
import cn.decentchina.bo.SubmitOrderBO;
import cn.decentchina.enums.ErrorCodeEnum;
import cn.decentchina.enums.OrderStatusEnum;
import cn.decentchina.enums.YesOrNo;
import cn.decentchina.exception.ErrorCodeException;
import cn.decentchina.mapper.OrderItemsMapper;
import cn.decentchina.mapper.OrderStatusMapper;
import cn.decentchina.mapper.OrdersMapper;
import cn.decentchina.pojo.*;
import cn.decentchina.vo.MerchantOrdersVO;
import cn.decentchina.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author jiangyu
 * @date 2020/1/28
 */
@Service
@Slf4j
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
    public void closeTimeOutOrder() {
        log.info("关闭订单定时任务执行");
        int row;
        OrderStatus status = new OrderStatus();
        status.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatus> statusList = orderStatusMapper.select(status);
        for (OrderStatus orderStatus : statusList) {
            if (DateUtils.addDays(orderStatus.getCreatedTime(), 1).before(new Date())) {
                orderStatus.setOrderStatus(OrderStatusEnum.WAIT_DELIVER.type);
                row = orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
                if (row != 1) {
                    throw new ErrorCodeException(ErrorCodeEnum.NO);
                }
                log.info("定时任务关闭超时未支付订单[{}]", orderStatus.getOrderId());
            }
        }
    }

    @Override
    public void updateOrderStatus(String merchantOrderId, Integer type) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(merchantOrderId);
        orderStatus.setOrderStatus(type);
        int row = orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
        if (row != 1) {
            throw new ErrorCodeException(ErrorCodeEnum.NO);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
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
        int totalAmount = 0, realPayAmount = 0, postAmount = 0;
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

        // 4. 构建商户订单，用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(submitOrderBO.getUserId());
        merchantOrdersVO.setAmount(realPayAmount + postAmount);
        merchantOrdersVO.setPayMethod(submitOrderBO.getPayMethod());

        // 5. 构建自定义订单vo
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);
        return orderVO;
    }
}
