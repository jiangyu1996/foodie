package cn.decentchina.mapper;

import cn.decentchina.pojo.OrderStatus;
import cn.decentchina.vo.ItemCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author jiangyu
 * @date 2020/1/14
 */
public interface OrderCustomMapper {

    /**
     * 查询订单
     *
     * @param map 参数
     * @return 订单集合
     */
    List<ItemCommentVO> queryMyOrders(@Param("paramsMap") Map<String, Object> map);


    /**
     * 查询订单数量
     *
     * @param map 参数
     * @return 订单数量
     */
    int getMyOrderStatusCounts(@Param("paramsMap") Map<String, Object> map);

    /**
     * 查询订单动向
     *
     * @param map 参数
     * @return 订单动向集合
     */
    List<OrderStatus> getMyOrderTrend(@Param("paramsMap") Map<String, Object> map);
}