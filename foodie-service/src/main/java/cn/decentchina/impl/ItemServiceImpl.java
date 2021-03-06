package cn.decentchina.impl;

import cn.decentchina.ItemService;
import cn.decentchina.base.BaseService;
import cn.decentchina.enums.CommentLevel;
import cn.decentchina.enums.ErrorCodeEnum;
import cn.decentchina.enums.YesOrNo;
import cn.decentchina.exception.ErrorCodeException;
import cn.decentchina.mapper.*;
import cn.decentchina.pojo.*;
import cn.decentchina.utils.DesensitizationUtil;
import cn.decentchina.utils.PagedGridResult;
import cn.decentchina.vo.CommentLevelCountsVO;
import cn.decentchina.vo.ItemCommentVO;
import cn.decentchina.vo.SearchItemsVO;
import cn.decentchina.vo.ShopcartVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author jiangyu
 * @date 2020/1/25
 */
@Service
public class ItemServiceImpl extends BaseService implements ItemService {
    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;
    @Autowired
    private ItemsCustomMapper itemsCustomMapper;
    @Autowired
    private ItemsCommentsCustomMapper itemsCommentsCustomMapper;

    @Override
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example itemsImgExp = new Example(ItemsImg.class);
        Example.Criteria criteria = itemsImgExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(itemsImgExp);
    }

    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example itemsSpecExp = new Example(ItemsSpec.class);
        Example.Criteria criteria = itemsSpecExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsSpecMapper.selectByExample(itemsSpecExp);
    }

    @Override
    public PagedGridResult catItems(Integer catId, String sort, Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("catId", catId);
        map.put("sort", sort);
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> searchItems = itemsCustomMapper.searchItemsByThirdCat(map);
        return getPagedGridResult(page, searchItems);
    }

    @Override
    public String queryItemMainImgById(String itemId) {
        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setItemId(itemId);
        itemsImg.setIsMain(YesOrNo.YES.type);
        itemsImg = itemsImgMapper.selectOne(itemsImg);
        return itemsImg == null ? "" : itemsImg.getUrl();
    }

    @Override
    public void decreaseItemSpecStock(String itemSpecId, int buyCounts) {
        int row = itemsCustomMapper.decreaseSpecStock(itemSpecId, buyCounts);
        if (row != 1) {
            throw new ErrorCodeException(ErrorCodeEnum.NO.getCode(), "库存不足");
        }
    }

    @Override
    public ItemsSpec querySpecById(String id) {
        return itemsSpecMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ShopcartVO> refresh(String itemSpecIds) {
        List<String> idList = Arrays.asList(itemSpecIds.split(","));
        return itemsCustomMapper.queryItemsBySpecIds(idList);
    }

    @Override
    public PagedGridResult search(String keyword, String sort, Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("keyword", keyword);
        map.put("sort", sort);
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> searchItems = itemsCustomMapper.searchItems(map);
        return getPagedGridResult(page, searchItems);
    }

    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("itemId", itemId);
        map.put("level", level);
        PageHelper.startPage(page, pageSize);
        List<ItemCommentVO> itemComments = itemsCommentsCustomMapper.queryItemComments(map);
        itemComments.forEach(o -> o.setNickname(DesensitizationUtil.commonDisplay(o.getNickname())));
        return getPagedGridResult(page, itemComments);
    }

    @Override
    public CommentLevelCountsVO commentLevel(String itemId) {
        CommentLevelCountsVO countsVO = new CommentLevelCountsVO();
        Integer goodComment = getCommentCount(itemId, CommentLevel.GOOD.type);
        Integer normalComment = getCommentCount(itemId, CommentLevel.NORMAL.type);
        Integer badComment = getCommentCount(itemId, CommentLevel.BAD.type);
        countsVO.setGoodCounts(goodComment);
        countsVO.setNormalCounts(normalComment);
        countsVO.setBadCounts(badComment);
        countsVO.setTotalCounts(goodComment + normalComment + badComment);
        return countsVO;
    }

    /**
     * 查询单个商品评论数量
     *
     * @param itemId 商品id
     * @param level  评论等级
     * @return 评论数量
     */
    private Integer getCommentCount(String itemId, Integer level) {
        ItemsComments itemsComments = new ItemsComments();
        itemsComments.setItemId(itemId);
        Optional.ofNullable(level).ifPresent(o -> itemsComments.setCommentLevel(level));
        return itemsCommentsMapper.selectCount(itemsComments);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example itemsParamExp = new Example(ItemsParam.class);
        Example.Criteria criteria = itemsParamExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsParamMapper.selectOneByExample(itemsParamExp);
    }
}

