package cn.decentchina.bo;

import lombok.Data;

/**
 * @author jiangyu
 */
@Data
public class ShopcartBO {

    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private Integer buyCounts;
    private String priceDiscount;
    private String priceNormal;

}
