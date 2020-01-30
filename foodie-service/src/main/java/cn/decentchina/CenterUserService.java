package cn.decentchina;

import cn.decentchina.bo.center.CenterUserBO;
import cn.decentchina.pojo.User;

/**
 * @author jiangyu
 * @date 2020/1/30
 */
public interface CenterUserService {

    /**
     * 会员id
     *
     * @param userId 用户id
     * @return 用户信息
     */
    User queryUserInfo(String userId);

    /**
     * 修改个人信息
     *
     * @param userId       用户id
     * @param centerUserBO 修改信息
     * @return 个人信息
     */
    User update(String userId, CenterUserBO centerUserBO);
}
