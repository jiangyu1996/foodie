package cn.decentchina.controller;

import cn.decentchina.entity.SimpleMessage;
import cn.decentchina.impl.ItemService;
import cn.decentchina.pojo.Items;
import cn.decentchina.pojo.ItemsImg;
import cn.decentchina.pojo.ItemsParam;
import cn.decentchina.pojo.ItemsSpec;
import cn.decentchina.vo.ItemInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
