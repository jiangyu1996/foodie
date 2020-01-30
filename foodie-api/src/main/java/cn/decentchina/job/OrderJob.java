package cn.decentchina.job;

import cn.decentchina.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author jiangyu
 * @date 2020/1/30
 */
@Component
public class OrderJob {
    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 0/10 * * * ? ")
    private void closeTimeOutOrder() {
        orderService.closeTimeOutOrder();
    }
}
