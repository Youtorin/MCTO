package com.yangdonglin.mcto.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("Food")
public class Food extends BaseEntity {

    /**
     * 商店ID
     */
    @TableField("shopId")
    private String shopId;

    /**
     * 分类ID
     */
    @TableField("cateId")
    private String cateId;

    /**
     * 分类名称
     */
    @TableField("cateName")
    private String cateName;

    /**
     * 食品名字
     */
    @TableField("title")
    private String title;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 食品封面图
     */
    @TableField("cover")
    private String cover;

    /**
     * 库存
     */
    @TableField("number")
    private Integer number;

    /**
     * 原价
     */
    @TableField("originPrice")
    private BigDecimal originPrice;

    /**
     * 售价
     */
    @TableField("sellPrice")
    private BigDecimal sellPrice;

    /**
     * 折扣
     */
    @TableField("discount")
    private BigDecimal discount;

    /**
     * 点赞
     */
    @TableField("likeNum")
    private Integer likeNum;

    /**
     * 限购数量
     */
    @TableField("limitNum")
    private Integer limitNum;

    /**
     * 规格选项
     */
    @TableField("options")
    private String options;

    /**
     * 总销量
     */
    @TableField("totalSales")
    private Integer totalSales;

    /**
     * 月销量
     */
    @TableField("monthSales")
    private Integer monthSales;

    /**
     * 好评率
     */
    @TableField("praiseRate")
    private Float praiseRate;

}
