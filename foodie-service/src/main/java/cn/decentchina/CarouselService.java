package cn.decentchina;

import cn.decentchina.pojo.Carousel;
import cn.decentchina.pojo.Category;
import cn.decentchina.vo.CategoryVO;

import java.util.List;

/**
 * @author jiangyu
 * @date 2020/1/25
 */
public interface CarouselService {
    /**
     * 查询所有轮播图
     *
     * @param isShow 是否显示
     * @return 轮播图集合
     */
    List<Carousel> listCarousel(Integer isShow);

    /**
     * 查询根节点分类
     *
     * @return 根节点集合
     */
    List<Category> listRootLevelCat();

    /**
     * 查询子节点分类
     *
     * @param rootCatId 父节点id
     * @return 子节点集合
     */
    List<CategoryVO> listSubCat(Integer rootCatId);
}
