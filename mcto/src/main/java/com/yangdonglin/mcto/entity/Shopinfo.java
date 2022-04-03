package com.yangdonglin.mcto.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yangdonglin.mcto.module.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商铺信息表
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ShopInfo")
public class Shopinfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 商店id
     */
    private String shopId;

    /**
     * 商店名称
     */
    private String shopname;

    /**
     * 联系人
     */
    private String contactMan;

    /**
     * 联系电话
     */
    private String contactMobile;

    /**
     * 门店类型
     */
    private Integer cateid;

    /**
     * 营业开始时间
     */
    private Integer beginTime;

    /**
     * 营业结束时间
     */
    private Integer endTime;

    /**
     * 门店图片
     */
    private String storeImg;

    /**
     * 店内图片
     */
    private String instoreImg;

    /**
     * logo图片
     */
    private String logoImg;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 外卖电话
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 商家公告
     */
    private String notice;

    /**
     * 平均评分
     */
    private Float score;

    /**
     * 配送时间
     */
    private String sendTime;

    /**
     * 配送费用
     */
    private BigDecimal sendCost;

    /**
     * 起送消费
     */
    private BigDecimal floorSendCost;


}
