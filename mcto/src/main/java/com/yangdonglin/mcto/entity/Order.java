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
@TableName("Order")
public class Order extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 商铺ID
     */
    private String shopId;

    /**
     * 配送费
     */
    private BigDecimal sendCost;

    /**
     * 总价
     */
    private BigDecimal totalMoney;

    /**
     * 实付金额
     */
    private BigDecimal payMoney;

    /**
     * 限定时间
     */
    private Integer demandTime;


}
