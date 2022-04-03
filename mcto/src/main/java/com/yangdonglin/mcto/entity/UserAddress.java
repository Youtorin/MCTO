package com.yangdonglin.mcto.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yangdonglin.mcto.module.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户配送地址表
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("UserAddress")
public class UserAddress extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableField("User_Id")
    private String userId;

    /**
     * 用户名
     */
    @TableField("UserName")
    private String username;

    /**
     * 性别
     */
    @TableField("gender")
    private String gender;

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 省
     */
    @TableField("province")
    private String province;

    /**
     * 市
     */
    @TableField("city")
    private String city;

    /**
     * 区
     */
    @TableField("district")
    private String district;

    /**
     * 经度
     */
    @TableField("longitude")
    private String longitude;

    /**
     * 维度
     */
    @TableField("latitude")
    private String latitude;

    /**
     * 详细地址
     */
    @TableField("address")
    private String address;

    /**
     * 街道门牌号
     */
    @TableField("street")
    private String street;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 是否默认地址
     */
    @TableField("is_Default")
    private Integer isDefault;


}
