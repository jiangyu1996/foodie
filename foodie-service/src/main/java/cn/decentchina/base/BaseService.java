package cn.decentchina.base;

import cn.decentchina.utils.PagedGridResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author jiangyu
 * @date 2020/2/1
 */
public class BaseService {

    public PagedGridResult getPagedGridResult(Integer page, List<?> list) {
        PageInfo<?> pageInfo = new PageInfo<>(list);
        PagedGridResult result = new PagedGridResult();
        result.setPage(page);
        result.setRows(pageInfo.getList());
        result.setRecords(pageInfo.getTotal());
        result.setTotal(pageInfo.getPages());
        return result;
    }

}
