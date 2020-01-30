package cn.decentchina.service.impl;

import cn.decentchina.enums.OrderStatusEnum;
import cn.decentchina.enums.YesOrNo;
import cn.decentchina.mapper.OrdersMapper;
import cn.decentchina.pojo.Orders;
import cn.decentchina.pojo.bo.MerchantOrdersBO;
import cn.decentchina.service.PaymentOrderService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class PaymentOrderServiceImpl implements PaymentOrderService {

	@Autowired
	private OrdersMapper ordersMapper;

	@Autowired
	private Sid sid;

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean createPaymentOrder(MerchantOrdersBO merchantOrdersBO) {

		String id = sid.nextShort();

		Orders paymentOrder = new Orders();
		BeanUtils.copyProperties(merchantOrdersBO, paymentOrder);

		paymentOrder.setId(id);
		paymentOrder.setPayStatus(OrderStatusEnum.WAIT_PAY.type);
		paymentOrder.setComeFrom("天天吃货");
		paymentOrder.setIsDelete(YesOrNo.NO.type);
		paymentOrder.setCreatedTime(new Date());

		int result = ordersMapper.insert(paymentOrder);
		return result == 1 ? true : false;
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	@Override
	public Orders queryOrderByStatus(String merchantUserId, String merchantOrderId, Integer orderStatus) {

		Orders queryOrder = new Orders();
		queryOrder.setMerchantOrderId(merchantOrderId);
		queryOrder.setMerchantUserId(merchantUserId);
		queryOrder.setPayStatus(orderStatus);
		Orders waitPayOrder = ordersMapper.selectOne(queryOrder);

		return waitPayOrder;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public String updateOrderPaid(String merchantOrderId, Integer paidAmount) {

		Example example = new Example(Orders.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("merchantOrderId", merchantOrderId);

		Orders paidOrder = new Orders();
		paidOrder.setPayStatus(OrderStatusEnum.PAID.type);
		paidOrder.setAmount(paidAmount);

		int result = ordersMapper.updateByExampleSelective(paidOrder, example);

		return queryMerchantReturnUrl(merchantOrderId);
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	String queryMerchantReturnUrl(String merchantOrderId) {

		Orders queryOrder = new Orders();
		queryOrder.setMerchantOrderId(merchantOrderId);
		Orders order = ordersMapper.selectOne(queryOrder);

		return order.getReturnUrl();
	}

	@Override
	public Orders queryOrderInfo(String merchantUserId, String merchantOrderId) {

		Orders orderInfo = new Orders();
		orderInfo.setMerchantOrderId(merchantOrderId);
		orderInfo.setMerchantUserId(merchantUserId);
		return ordersMapper.selectOne(orderInfo);
	}
}
