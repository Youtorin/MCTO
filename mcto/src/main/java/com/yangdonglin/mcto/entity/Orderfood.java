package com.yangdonglin.mcto.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yangdonglin.mcto.module.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单商品详情表
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("OrderFood")
public class Orderfood extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 商铺ID
     */
    private String shopId;

    /**
     * 商铺名称
     */
    private String shopname;

    /**
     * 商品ID
     */
    private String foodId;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品封面
     */
    private String cover;

    /**
     * 售价
     */
    private BigDecimal sellPrice;

    /**
     * 下单数量
     */
    private Integer number;


}
