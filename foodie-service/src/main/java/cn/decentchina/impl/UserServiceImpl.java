package cn.decentchina.impl;

import cn.decentchina.UserService;
import cn.decentchina.bo.UserBO;
import cn.decentchina.enums.Sex;
import cn.decentchina.mapper.UsersMapper;
import cn.decentchina.pojo.User;
import cn.decentchina.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author jiangyu
 * @date 2020/1/18
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private Sid sid;

    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";


    @Override
    public boolean checkUserExists(String userName) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", userName);
        User user = usersMapper.selectOneByExample(example);
        return Objects.nonNull(user);
    }

    @Override
    public User createUser(UserBO userBO) {
        String userId = sid.nextShort();

        User user = User.builder().id(userId).username(userBO.getUsername()).nickname(userBO.getUsername())
                .face(USER_FACE).birthday("1970-01-01").sex(Sex.secret.type).createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now()).build();
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        usersMapper.insert(user);
        return user;
    }
}
