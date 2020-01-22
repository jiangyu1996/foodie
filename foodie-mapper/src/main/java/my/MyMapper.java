package my;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author jiangyu
 * @date 2020/1/14
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
