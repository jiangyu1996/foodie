package cn.decentchina.mapper;

import cn.decentchina.vo.ItemCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author jiangyu
 * @date 2020/1/14
 */
public interface ItemsCustomCommentsMapper {

    /**
     * 查询评价
     *
     * @param map 参数
     * @return 评价集合
     */
    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);


}