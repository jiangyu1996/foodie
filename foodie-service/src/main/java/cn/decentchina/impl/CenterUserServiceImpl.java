package cn.decentchina.impl;

import cn.decentchina.CenterUserService;
import cn.decentchina.bo.center.CenterUserBO;
import cn.decentchina.enums.ErrorCodeEnum;
import cn.decentchina.exception.ErrorCodeException;
import cn.decentchina.mapper.UsersMapper;
import cn.decentchina.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author jiangyu
 * @date 2020/1/30
 */
@Service
public class CenterUserServiceImpl implements CenterUserService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public User update(String userId, CenterUserBO centerUserBO) {
        User user = new User();
        BeanUtils.copyProperties(centerUserBO, user);
        user.setId(userId);
        user.setUpdatedTime(LocalDateTime.now());
        int row = usersMapper.updateByPrimaryKeySelective(user);
        if (row != 1) {
            throw new ErrorCodeException(ErrorCodeEnum.NO);
        }
        return user;
    }

    @Override
    public User queryUserInfo(String userId) {
        User user = new User();
        user.setId(userId);
        user = usersMapper.selectOne(user);
        Optional.ofNullable(user).ifPresent(o -> o.setPassword(null));
        return user;
    }
}
