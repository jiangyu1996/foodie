package cn.decentchina;

import cn.decentchina.bo.center.OrderItemsCommentBO;
import cn.decentchina.pojo.OrderItems;
import cn.decentchina.utils.PagedGridResult;

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

    /**
     * 保存评论
     *
     * @param orderId     订单id
     * @param userId      用户id
     * @param commentList 评价列表
     */
    void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);

    /**
     * 查询历史评价
     *
     * @param userId   用户id
     * @param page     分页查询页数
     * @param pageSize 分页大小
     * @return 分页评价信息
     */
    PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
