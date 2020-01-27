package cn.decentchina.controller;

import cn.decentchina.entity.SimpleMessage;
import cn.decentchina.ItemService;
import cn.decentchina.pojo.Items;
import cn.decentchina.pojo.ItemsImg;
import cn.decentchina.pojo.ItemsParam;
import cn.decentchina.pojo.ItemsSpec;
import cn.decentchina.utils.PagedGridResult;
import cn.decentchina.vo.CommentLevelCountsVO;
import cn.decentchina.vo.ItemInfoVO;
import cn.decentchina.vo.ShopcartVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jiangyu
 * @date 2020/1/25
 */
@RestController
@RequestMapping("items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("info/{itemId}")
    public SimpleMessage info(@PathVariable String itemId) {
        if (itemId == null) {
            return new SimpleMessage();
        }
        ItemInfoVO itemInfoVO = new ItemInfoVO();
        Items items = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgs = itemService.queryItemImgList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        List<ItemsSpec> itemsSpecs = itemService.queryItemSpecList(itemId);
        itemInfoVO.setItem(items);
        itemInfoVO.setItemImgList(itemsImgs);
        itemInfoVO.setItemParams(itemsParam);
        itemInfoVO.setItemSpecList(itemsSpecs);
        return new SimpleMessage(itemInfoVO);
    }

    @GetMapping("commentLevel")
    public SimpleMessage commentLevel(@RequestParam String itemId) {
        CommentLevelCountsVO countsVO = itemService.commentLevel(itemId);
        return new SimpleMessage(countsVO);
    }

    @GetMapping("comments")
    public SimpleMessage comments(@RequestParam String itemId, @RequestParam Integer level,
                                  @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer pageSize) {
        PagedGridResult result = itemService.queryPagedComments(itemId, level, page, pageSize);
        return new SimpleMessage(result);
    }

    @GetMapping("search")
    public SimpleMessage search(@RequestParam String keywords, @RequestParam String sort,
                                @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer pageSize) {
        PagedGridResult result = itemService.search(keywords, sort, page, pageSize);
        return new SimpleMessage(result);
    }

    @GetMapping("catItems")
    public SimpleMessage catItems(@RequestParam Integer catId, @RequestParam String sort,
                                  @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer pageSize) {
        PagedGridResult result = itemService.catItems(catId, sort, page, pageSize);
        return new SimpleMessage(result);
    }

    /**
     * 用户长时间未登录购物车，同步刷新价格
     *
     * @param itemSpecIds 产品价格
     * @return 产品价格信息
     */
    @GetMapping("refresh")
    public SimpleMessage refresh(@RequestParam String itemSpecIds) {
        if (StringUtils.isBlank(itemSpecIds)) {
            return new SimpleMessage();
        }
        List<ShopcartVO> refresh = itemService.refresh(itemSpecIds);
        return new SimpleMessage(refresh);
    }

}
