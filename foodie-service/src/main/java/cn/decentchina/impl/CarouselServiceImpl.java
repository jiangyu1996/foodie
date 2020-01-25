package cn.decentchina.impl;

import cn.decentchina.CarouselService;
import cn.decentchina.enums.YesOrNo;
import cn.decentchina.mapper.CarouselMapper;
import cn.decentchina.mapper.CategoryCustomMapper;
import cn.decentchina.mapper.CategoryMapper;
import cn.decentchina.pojo.Carousel;
import cn.decentchina.pojo.Category;
import cn.decentchina.vo.CategoryVO;
import cn.decentchina.vo.NewItemsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author jiangyu
 * @date 2020/1/25
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryCustomMapper categoryCustomMapper;

    @Override
    public List<CategoryVO> listSubCat(Integer rootCatId) {
        return categoryCustomMapper.getSubCatList(rootCatId);
    }

    @Override
    public List<NewItemsVO> getSixNewItemsLazy(int rootCatId) {
        return categoryCustomMapper.getSixNewItemsLazy(rootCatId);
    }

    @Override
    public List<Category> listRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", YesOrNo.YES.type);
        return categoryMapper.selectByExample(example);
    }

    @Override
    public List<Carousel> listCarousel(Integer isShow) {
        Example example = new Example(Carousel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow", true);
        example.orderBy("sort").desc();
        return carouselMapper.selectByExample(example);
    }
}
