package cn.decentchina.mapper;

import cn.decentchina.vo.SearchItemsVO;
import cn.decentchina.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author jiangyu
 * @date 2020/1/14
 */
public interface ItemsCustomMapper {

    /**
     * 关键字查询产品
     *
     * @param map 参数
     * @return 评价集合
     */
    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);

    /**
     * 导航栏查询产品
     *
     * @param map 参数
     * @return 评价集合
     */
    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);

    /**
     * 导航栏查询产品
     *
     * @param map 参数
     * @return 评价集合
     */
    List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List<String> map);


    /**
     * 扣除库存
     *
     * @param specId        产品id
     * @param pendingCounts 购买数量
     * @return 评价集合
     */
    int decreaseSpecStock(@Param("specId") String specId, @Param("pendingCounts") int pendingCounts);


}