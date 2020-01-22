package cn.decentchina;

import cn.decentchina.bo.UserBO;
import cn.decentchina.pojo.User;

/**
 * @author jiangyu
 * @date 2020/1/18
 */
public interface UserService {

    /**
     * 判断用户是否存在
     *
     * @param userName 用户名
     * @return 是否存在(true : 存在 ; false : 不存在)
     */
    boolean checkUserExists(String userName);

    /**
     * 创建用户
     *
     * @param userBO 用户信息
     * @return 用户信息
     */
    User createUser(UserBO userBO);
}
