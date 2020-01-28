package cn.decentchina;

import cn.decentchina.bo.AddressBO;
import cn.decentchina.pojo.UserAddress;

import java.util.List;

/**
 * @author jiangyu
 * @date 2020/1/27
 */
public interface UserAddressService {

    /**
     * 查询用户地址
     *
     * @param userId 用户地址
     * @return 用户地址集合
     */
    List<UserAddress> listAddress(String userId);

    /**
     * 添加收货地址
     *
     * @param addressBO 收获信息
     */
    void add(AddressBO addressBO);

    /**
     * 修改地址
     *
     * @param addressBO 地址变更信息
     */
    void update(AddressBO addressBO);

    /**
     * 删除地址
     *
     * @param userId    用户id
     * @param addressId 地址记录id
     */
    void delete(String userId, String addressId);

    /**
     * 设置默认地址
     *
     * @param userId    用户id
     * @param addressId 地址记录id
     */
    void setDefault(String userId, String addressId);

    /**
     * 查询地址
     *
     * @param userId    用户id
     * @param addressId 地址id
     * @return 地址信息
     */
    UserAddress queryUserAddress(String userId, String addressId);
}
