package com.yangdonglin.mcto.mapper;

import com.yangdonglin.mcto.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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

}
