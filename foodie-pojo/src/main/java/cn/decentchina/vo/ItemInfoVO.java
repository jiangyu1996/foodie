package cn.decentchina.vo;


import cn.decentchina.pojo.Items;
import cn.decentchina.pojo.ItemsImg;
import cn.decentchina.pojo.ItemsParam;
import cn.decentchina.pojo.ItemsSpec;
import lombok.Data;

import java.util.List;

/**
 * 商品详情VO
 */
@Data
public class ItemInfoVO {

    private Items item;
    private List<ItemsImg> itemImgList;
    private List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;

}
