package com.yangdonglin.mcto.entity;

import java.math.BigDecimal;
import java.util.Date;

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
     * 用户地址与商铺地址距离
     */
    private Double distance;

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
     * 退回款项
     */
    private BigDecimal payMoney;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 卖家处理时间
     */
    private Date handleTime;

    /**
     * 退款完成时间
     */
    private Date finishTime;

    /**
     * 退款状态 1-退款中 2-已完成 3-已取消
     */
    private Integer refundStatus;

    /**
     * 限定时间
     */
    private Integer demandTime;


}
