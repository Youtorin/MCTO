package com.yangdonglin.mcto.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yangdonglin.mcto.module.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 食品分类表
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("CFG_FoodCategory")
public class CfgFoodcategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 商店ID
     */
    private String shopId;

    /**
     * 分类类型
     */
    private String name;

    /**
     * 描述
     */
    private String desc;


}
