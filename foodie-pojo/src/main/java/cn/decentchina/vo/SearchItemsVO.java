package cn.decentchina.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 用于展示商品搜索列表结果的VO
 */
@Data
public class SearchItemsVO {

    private String itemId;
    private String itemName;
    private Integer sellCounts;
    private String imgUrl;
    private BigDecimal price;

}
