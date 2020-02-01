package cn.decentchina.mapper;

import cn.decentchina.vo.ItemCommentVO;
import cn.decentchina.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author jiangyu
 * @date 2020/1/14
 */
public interface ItemsCommentsCustomMapper {

    /**
     * 查询订单评价
     *
     * @param map 参数
     * @return 评价列表
     */
    List<MyCommentVO> queryMyComments(Map<String, Object> map);

    /**
     * 查询评价
     *
     * @param map 参数
     * @return 评价集合
     */
    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);

    /**
     * 保存评价
     *
     * @param map 参数map
     */
    void saveComments(Map<String, Object> map);
}