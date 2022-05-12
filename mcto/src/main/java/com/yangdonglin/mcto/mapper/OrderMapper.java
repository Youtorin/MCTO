package com.yangdonglin.mcto.mapper;

import com.yangdonglin.mcto.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 订单主表 Mapper 接口
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Select("<script>" +
            "SELECT `Status`,COUNT(id) as count FROM `Order` where `shopid` = #{shopid} GROUP BY `Status`" +
            "</script>")
    List<HashMap<Integer, Integer>> selectCount(@Param("shopid") String shopid);
}
