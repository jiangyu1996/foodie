package cn.decentchina.controller.center;

import cn.decentchina.MyCommentsService;
import cn.decentchina.MyOrderService;
import cn.decentchina.bo.center.OrderItemsCommentBO;
import cn.decentchina.entity.SimpleMessage;
import cn.decentchina.enums.YesOrNo;
import cn.decentchina.exception.ErrorCodeException;
import cn.decentchina.pojo.OrderItems;
import cn.decentchina.pojo.Orders;
import cn.decentchina.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author jiangyu
 * @date 2020/1/31
 */
@RestController
@RequestMapping("mycomments")
public class MyCommentsController {

    @Autowired
    private MyCommentsService myCommentsService;
    @Autowired
    private MyOrderService myOrderService;

    @PostMapping("/pending")
    public SimpleMessage pending(@RequestParam String userId, @RequestParam String orderId) {
        // 判断用户和订单是否关联
        Orders orders = checkUserOrder(userId, orderId);
        Optional.of(orders).ifPresent(o -> {
            if (YesOrNo.YES.type.equals(o.getIsComment())) {
                throw new ErrorCodeException("该笔订单已经评价");
            }
        });
        List<OrderItems> list = myCommentsService.queryPendingComment(orderId);
        return new SimpleMessage(list);
    }


    @PostMapping("/saveList")
    public SimpleMessage saveList(@RequestParam String userId, @RequestParam String orderId, @RequestBody List<OrderItemsCommentBO> commentList) {
        // 判断用户和订单是否关联
        checkUserOrder(userId, orderId);
        // 判断评论内容list不能为空
        if (CollectionUtils.isEmpty(commentList)) {
            throw new ErrorCodeException("评论内容不能为空！");
        }
        myCommentsService.saveComments(orderId, userId, commentList);
        return new SimpleMessage();
    }

    @PostMapping("/query")
    public SimpleMessage query(@RequestParam String userId, @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        PagedGridResult grid = myCommentsService.queryMyComments(userId, page, pageSize);
        return new SimpleMessage(grid);
    }

    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     */
    private Orders checkUserOrder(String userId, String orderId) {
        Orders order = myOrderService.queryMyOrder(userId, orderId);
        if (order == null) {
            throw new ErrorCodeException("订单不存在！");
        }
        return order;
    }
}
