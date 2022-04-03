package com.yangdonglin.mcto.service.impl;

import com.yangdonglin.mcto.entity.Order;
import com.yangdonglin.mcto.mapper.OrderMapper;
import com.yangdonglin.mcto.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单主表 服务实现类
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
