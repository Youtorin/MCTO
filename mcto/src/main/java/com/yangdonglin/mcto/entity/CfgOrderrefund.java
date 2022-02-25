package com.yangdonglin.mcto.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yangdonglin.mcto.module.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单--退款处理
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("CFG_OrderRefund")
public class CfgOrderrefund extends BaseEntity {

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
     * 用户名
     */
    private String username;

    /**
     * 需要退回的款项
     */
    private BigDecimal payMoney;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 申请时间
     */
    private Integer applyTime;

    /**
     * 卖家处理时间
     */
    private Integer handleTime;

    /**
     * 退款完成时间
     */
    private Integer finishTime;


}
