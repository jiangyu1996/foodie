package cn.decentchina.controller;

import cn.decentchina.UserAddressService;
import cn.decentchina.bo.AddressBO;
import cn.decentchina.entity.SimpleMessage;
import cn.decentchina.enums.ErrorCodeEnum;
import cn.decentchina.exception.ErrorCodeException;
import cn.decentchina.pojo.UserAddress;
import cn.decentchina.utils.MobileEmailUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @author jiangyu
 * @date 2020/1/27
 */
@RestController
@RequestMapping("address")
public class AddressController {

    /**
     * 地址长度
     */
    private final static int MAX_ADDRESS_LEN = 12;
    /**
     * 手机号长度
     */
    private final static int MOBILE_LEN = 11;


    @Autowired
    private UserAddressService userAddressService;

    @PostMapping("list")
    public SimpleMessage listAddress(String userId) {
        if (StringUtils.isBlank(userId)) {
            return new SimpleMessage(Collections.emptyList());
        }
        List<UserAddress> userAddresses = userAddressService.listAddress(userId);
        return new SimpleMessage(userAddresses);
    }

    @PostMapping("add")
    public SimpleMessage add(@RequestBody AddressBO addressBO) {
        checkAddress(addressBO);
        userAddressService.add(addressBO);
        return new SimpleMessage();
    }

    @PostMapping("update")
    public SimpleMessage update(@RequestBody AddressBO addressBO) {
        if (StringUtils.isBlank(addressBO.getAddressId())) {
            throw new ErrorCodeException(ErrorCodeEnum.INVALID_PARAMS);
        }
        userAddressService.update(addressBO);
        return new SimpleMessage();
    }

    @PostMapping("delete")
    public SimpleMessage delete(@RequestParam String userId, @RequestParam String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            throw new ErrorCodeException(ErrorCodeEnum.INVALID_PARAMS);
        }
        userAddressService.delete(userId, addressId);
        return new SimpleMessage();
    }

    @PostMapping("setDefalut")
    public SimpleMessage setDefault(@RequestParam String userId, @RequestParam String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            throw new ErrorCodeException(ErrorCodeEnum.INVALID_PARAMS);
        }
        userAddressService.setDefault(userId, addressId);
        return new SimpleMessage();
    }

    private void checkAddress(AddressBO addressBO) {
        if (addressBO == null) {
            throw new ErrorCodeException(ErrorCodeEnum.INVALID_PARAMS);
        }
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            throw new ErrorCodeException(ErrorCodeEnum.INVALID_PARAMS.getCode(), "收货人不能为空");
        }
        if (receiver.length() > MAX_ADDRESS_LEN) {
            throw new ErrorCodeException(ErrorCodeEnum.INVALID_PARAMS.getCode(), "收货人姓名不能太长");
        }
        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            throw new ErrorCodeException(ErrorCodeEnum.INVALID_PARAMS.getCode(), "收货人手机号不能为空");
        }
        if (mobile.length() != MOBILE_LEN) {
            throw new ErrorCodeException(ErrorCodeEnum.INVALID_PARAMS.getCode(), "收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            throw new ErrorCodeException(ErrorCodeEnum.INVALID_PARAMS.getCode(), "收货人手机号格式不正确");
        }
        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) || StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) || StringUtils.isBlank(detail)) {
            throw new ErrorCodeException(ErrorCodeEnum.INVALID_PARAMS.getCode(), "收货地址信息不能为空");
        }
    }
}
