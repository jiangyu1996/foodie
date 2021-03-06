package cn.decentchina.controller;

import cn.decentchina.CarouselService;
import cn.decentchina.entity.RedisKey;
import cn.decentchina.entity.SimpleMessage;
import cn.decentchina.enums.YesOrNo;
import cn.decentchina.pojo.Carousel;
import cn.decentchina.pojo.Category;
import cn.decentchina.vo.CategoryVO;
import cn.decentchina.vo.NewItemsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
@RequestMapping("index")
public class IndexController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CarouselService carouselService;

    @GetMapping("carousel")
    public SimpleMessage carousel() {
        List<Carousel> carousels = (List<Carousel>) redisTemplate.opsForValue().get(RedisKey.CAROUSEL_KEY);
        if (carousels == null) {
            carousels = carouselService.listCarousel(YesOrNo.YES.type);
            redisTemplate.opsForValue().set(RedisKey.CAROUSEL_KEY, carousels);

        }
        return new SimpleMessage(carousels);
    }

    @GetMapping("cats")
    public SimpleMessage cats() {
        List<Category> categories = (List<Category>) redisTemplate.opsForValue().get(RedisKey.CATS_KEY);
        if (categories == null) {
            categories = carouselService.listRootLevelCat();
            redisTemplate.opsForValue().set(RedisKey.CATS_KEY, categories);
        }
        return new SimpleMessage(categories);
    }

    @GetMapping("subCat/{rootCatId}")
    public SimpleMessage subCat(@PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return new SimpleMessage();
        }
        List<CategoryVO> categoryVOList = (List<CategoryVO>) redisTemplate.opsForValue().get(RedisKey.SUB_CAT_KEY + rootCatId);
        if (categoryVOList == null) {
            categoryVOList = carouselService.listSubCat(rootCatId);
            redisTemplate.opsForValue().set(RedisKey.SUB_CAT_KEY + rootCatId, categoryVOList);
        }
        return new SimpleMessage(categoryVOList);
    }

    @GetMapping("sixNewItems/{rootCatId}")
    public SimpleMessage getSixNewItemsLazy(@PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return new SimpleMessage();
        }
        List<NewItemsVO> categoryVOList = carouselService.getSixNewItemsLazy(rootCatId);
        return new SimpleMessage(categoryVOList);
    }

}
