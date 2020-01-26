package cn.decentchina.impl;

import cn.decentchina.pojo.Items;
import cn.decentchina.pojo.ItemsImg;
import cn.decentchina.pojo.ItemsParam;
import cn.decentchina.pojo.ItemsSpec;
import cn.decentchina.utils.PagedGridResult;
import cn.decentchina.vo.CommentLevelCountsVO;

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

    /**
     * 查询评价数量
     *
     * @param itemId 产品id
     * @return 评论数量
     */
    CommentLevelCountsVO commentLevel(String itemId);

    /**
     * 查询评价数量
     *
     * @param itemId   产品id
     * @param level    评论等级
     * @param page     查询页数
     * @param pageSize 分页查询数量
     * @return 评论集合
     */
    PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 关键字查询产品
     *
     * @param keyword  关键字
     * @param sort     排序规则
     * @param page     查询页数
     * @param pageSize 分页查询数量
     * @return 产品集合
     */
    PagedGridResult search(String keyword, String sort, Integer page, Integer pageSize);

    /**
     * 导航栏分类查询产品
     *
     * @param catId    分类id
     * @param sort     排序规则
     * @param page     查询页数
     * @param pageSize 分页查询数量
     * @return 产品集合
     */
    PagedGridResult catItems(Integer catId, String sort, Integer page, Integer pageSize);
}
