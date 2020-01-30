package cn.decentchina;

import cn.decentchina.bo.UserBO;
import cn.decentchina.pojo.User;

/**
 * @author jiangyu
 * @date 2020/1/18
 */
public interface UserService {

    /**
     * 根据用户名查询用户
     *
     * @param userName 用户名
     * @return 用户信息
     */
    User queryByName(String userName);

    /**
     * 创建用户
     *
     * @param userBO 用户信息
     * @return 用户信息
     */
    User createUser(UserBO userBO);

    /**
     * 跟新头像
     *
     * @param userId       用户id
     * @param serverImgUrl 图片url
     * @return 用户信息
     */
    User updateFaceUrl(String userId, String serverImgUrl);
}
