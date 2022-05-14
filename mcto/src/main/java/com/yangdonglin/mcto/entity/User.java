package com.yangdonglin.mcto.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yangdonglin.mcto.module.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户登录表
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("User")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户头像
     */
    private String img;

    /**
     * 钱包
     */
    private BigDecimal wallet;

    /**
     * 访问次数
     */
    private Integer viewNum;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户真实姓名
     */
    private String truename;

    /**
     * 性别
     */
    private String gender;


}
