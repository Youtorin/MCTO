package com.yangdonglin.mcto.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yangdonglin.mcto.module.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商家登录表
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("Shop")
public class Shop extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 商店名称
     */
    @TableField("shopname")
    private String shopname;

    /**
     * 商店账号
     */
    @TableField("shopAccount")
    private String shopAccount;

    /**
     * 商店密码
     */
    @TableField("password")
    private String password;

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 邮箱
     */
    @TableField("remark")
    private String remark;


}
