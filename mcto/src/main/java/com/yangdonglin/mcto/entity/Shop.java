package com.yangdonglin.mcto.entity;

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
    private String shopname;

    /**
     * 商店密码
     */
    private String password;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;


}
