package com.yangdonglin.mcto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangdonglin.mcto.entity.Statistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 统计 接口
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@Mapper
public interface StatisticsMapper extends BaseMapper<Statistics> {

    @Select("<script>" +
            "select sum(totalMoney) from `Order` where  (refundStatus != 2 or refundStatus is null) " +
            "</script>")
    BigDecimal getTotalSales();

    @Select("<script>" +
            "select count(*) as count,status from `Order` GROUP BY status " +
            "</script>")
    List<HashMap<Integer,Integer>> getOrderByCate();

    @Select("<script>" +
            "select Count(*) from OrderComment where `Status` = 1 " +
            "</script>")
    Integer getTotalComment();

    @Select("<script>" +
            "select sum(viewNum) as count from `User` " +
            "</script>")
    Integer getTotalView();

    @Select("<script>" +
            "select count(*) as count from `User` WHERE status = 1 " +
            "</script>")
    Integer getTotalUser();

    @Select("<script>" +
            "select count(*) as count,sum(number) as sum from Food where status = 1 " +
            "</script>")
    HashMap<Integer,Integer> getFoodAndCount();

    @Select("<script>" +
            "select count(*) from FoodCategory where STATUS = 1 " +
            "</script>")
    Integer getTotalCate();
}
