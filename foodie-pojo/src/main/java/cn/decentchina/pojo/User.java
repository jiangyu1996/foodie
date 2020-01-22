package cn.decentchina.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author jiangyu
 * @date 2020/1/14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * 主键id 用户id
     */
    @Id
    private String id;

    /**
     * 用户名 用户名
     */
    private String username;

    /**
     * 密码 密码
     */
    private String password;

    /**
     * 昵称 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 头像
     */
    private String face;

    /**
     * 手机号 手机号
     */
    private String mobile;

    /**
     * 邮箱地址 邮箱地址
     */
    private String email;

    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    private Integer sex;

    /**
     * 生日 生日
     */
    private String birthday;

    /**
     * 创建时间 创建时间
     */
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    /**
     * 更新时间 更新时间
     */
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

}