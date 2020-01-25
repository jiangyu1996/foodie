package cn.decentchina.impl;

import cn.decentchina.pojo.Items;
import cn.decentchina.pojo.ItemsImg;
import cn.decentchina.pojo.ItemsParam;
import cn.decentchina.pojo.ItemsSpec;

import java.util.List;

/**
 * @author jiangyu
 * @date 2020/1/25
 */
public interface ItemService {

    /**
     * 根据商品ID查询详情
     *
     * @param itemId 产品id
     * @return 产品信息
     */
    Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品图片列表
     *
     * @param itemId 产品id
     * @return 产品信息
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格
     *
     * @param itemId 产品id
     * @return 产品信息
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     *
     * @param itemId 产品id
     * @return 产品信息
     */
    ItemsParam queryItemParam(String itemId);
}
