package cn.decentchina.vo;

import lombok.Data;

/**
 * 用于展示商品评价数量的vo
 */
@Data
public class CommentLevelCountsVO {

    /**
     * 总评价数量
     */
    public Integer totalCounts;
    /**
     * 好评数量
     */
    public Integer goodCounts;
    /**
     * 中评数量
     */
    public Integer normalCounts;
    /**
     * 差评数量
     */
    public Integer badCounts;

}
