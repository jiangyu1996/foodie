package cn.decentchina.impl;

import cn.decentchina.UserAddressService;
import cn.decentchina.bo.AddressBO;
import cn.decentchina.enums.ErrorCodeEnum;
import cn.decentchina.enums.YesOrNo;
import cn.decentchina.exception.ErrorCodeException;
import cn.decentchina.mapper.UserAddressMapper;
import cn.decentchina.pojo.UserAddress;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author jiangyu
 * @date 2020/1/27
 */
@Service
public class UserUserAddressServiceImpl implements UserAddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private Sid sid;

    @Override
    public void setDefault(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setIsDefault(YesOrNo.NO.type);
        Example example = new Example(UserAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        userAddressMapper.updateByExampleSelective(userAddress, example);
        userAddress.setId(addressId);
        userAddress.setIsDefault(YesOrNo.YES.type);
        userAddress.setUpdatedTime(new Date());
        int insert = userAddressMapper.updateByPrimaryKeySelective(userAddress);
        if (insert != 1) {
            throw new ErrorCodeException(ErrorCodeEnum.NO);
        }
    }

    @Override
    public void delete(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setId(addressId);
        userAddress.setUserId(userId);
        int insert = userAddressMapper.delete(userAddress);
        if (insert != 1) {
            throw new ErrorCodeException(ErrorCodeEnum.NO);
        }
    }

    @Override
    public void update(AddressBO addressBO) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setId(addressBO.getAddressId());
        userAddress.setUpdatedTime(new Date());
        int insert = userAddressMapper.updateByPrimaryKeySelective(userAddress);
        if (insert != 1) {
            throw new ErrorCodeException(ErrorCodeEnum.NO);
        }
    }

    @Override
    public void add(AddressBO addressBO) {
        List<UserAddress> userAddresses = listAddress((addressBO.getUserId()));
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setIsDefault(0);
        if (CollectionUtils.isEmpty(userAddresses)) {
            userAddress.setIsDefault(1);
        }
        userAddress.setId(sid.nextShort());
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        int insert = userAddressMapper.insert(userAddress);
        if (insert != 1) {
            throw new ErrorCodeException(ErrorCodeEnum.NO);
        }
    }

    @Override
    public List<UserAddress> listAddress(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }
}
