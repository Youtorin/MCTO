package com.yangdonglin.mcto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangdonglin.mcto.entity.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 商家登录表 Mapper 接口
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@Mapper
public interface ShopMapper extends BaseMapper<Shop> {

    @Select("<script>" +
            "select shopname,ShopAccount,password,password,email " +
            "from Shop " +
            "where ShopAccount=#{account}" +
            "</script>")
    Shop getShopAccount(@Param("account") String account);
}
