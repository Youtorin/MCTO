package com.yangdonglin.mcto.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yangdonglin.mcto.module.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单主表
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("`Order`")
public class Order extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户联系电话
     */
    private String userMobile;

    /**
     * 用户地址ID
     */
    private String userAddressId;

    /**
     * 用户详细地址
     */
    private String userAddress;

    /**
     * 用户备注内容
     */
    private String remark;

    /**
     * 商铺ID
     */
    private String shopId;

    /**
     * 商铺名字
     */
    private String shopname;

    /**
     * 商铺联系电话
     */
    private String shopMobile;

    /**
     * 商铺详细地址
     */
    private String shopAddress;

    /**
     * 商品详情
     */
    private String food;

    /**
     * 配送费
     */
    private BigDecimal sendCost;

    /**
     * 总价
     */
    private BigDecimal totalMoney;

    /**
     * 配送方式 0-送货上门  1-自取
     */
    private Integer distributionType;

    /**
     * 实付金额
     */
    private BigDecimal payMoney;

    /**
     * 限定时间
     */
    private Integer demandTime;


}
