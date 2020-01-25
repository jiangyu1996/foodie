package cn.decentchina.mapper;

import cn.decentchina.vo.CategoryVO;
import cn.decentchina.vo.NewItemsVO;

import java.util.List;

/**
 * @author jiangyu
 * @date 2020/1/25
 */
public interface CategoryCustomMapper {

    /**
     * 根据父级节点查询子节点信息
     *
     * @param rootCatId 父节点id
     * @return 子节点集合
     */
    List<CategoryVO> getSubCatList(int rootCatId);

    /**
     * 根据父级节点下最新产品信息
     *
     * @param rootCatId 父节点id
     * @return 子节点集合
     */
    List<NewItemsVO> getSixNewItemsLazy(int rootCatId);

}
