package com.yangdonglin.mcto.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yangdonglin.mcto.module.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单详情表
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("OrderDetail")
public class Orderdetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 用户名
     */
    private String userUsername;

    /**
     * 用户联系电话
     */
    private String userMobile;

    /**
     * 用户地址ID
     */
    private String UserAddressId;

    /**
     * 经度
     */
    private String userLongitude;

    /**
     * 纬度
     */
    private String userLatitude;

    /**
     * 用户详细地址
     */
    private String UserAddress;

    /**
     * 商铺名字
     */
    private String shopShopname;

    /**
     * 商铺联系电话
     */
    private String shopMobile;

    /**
     * 商铺详细地址
     */
    private String shopAddress;


}
