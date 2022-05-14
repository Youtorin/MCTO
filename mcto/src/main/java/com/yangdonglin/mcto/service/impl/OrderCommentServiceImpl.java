package com.yangdonglin.mcto.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangdonglin.mcto.entity.OrderComment;
import com.yangdonglin.mcto.entity.Orderdetail;
import com.yangdonglin.mcto.mapper.OrderCommentMapper;
import com.yangdonglin.mcto.mapper.OrderdetailMapper;
import com.yangdonglin.mcto.service.OrderCommentService;
import com.yangdonglin.mcto.service.OrderdetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderCommentServiceImpl extends ServiceImpl<OrderCommentMapper, OrderComment> implements OrderCommentService {
}
