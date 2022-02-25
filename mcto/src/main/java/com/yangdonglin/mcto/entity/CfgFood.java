package com.yangdonglin.mcto.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yangdonglin.mcto.module.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜品信息表
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("CFG_Food")
public class CfgFood extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 商店ID
     */
    private String shopId;

    /**
     * 分类ID
     */
    private String cateId;

    /**
     * 食品名字
     */
    private String title;

    /**
     * 描述
     */
    private String desc;

    /**
     * 食品封面图
     */
    private String cover;

    /**
     * 原价
     */
    private BigDecimal originPrice;

    /**
     * 售价
     */
    private BigDecimal sellPrice;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 点赞
     */
    private Integer like;

    /**
     * 限购数量
     */
    private Integer limitNum;

    /**
     * 规格选项
     */
    private String option;

    /**
     * 总销量
     */
    private Integer totalSales;

    /**
     * 月销量
     */
    private Integer monthSales;

    /**
     * 好评率
     */
    private Float praiseRate;


}
