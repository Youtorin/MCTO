package com.yangdonglin.mcto.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yangdonglin.mcto.entity.Order;
import com.yangdonglin.mcto.entity.OrderComment;
import com.yangdonglin.mcto.entity.User;
import com.yangdonglin.mcto.module.AjaxResponse;
import com.yangdonglin.mcto.service.OrderCommentService;
import com.yangdonglin.mcto.service.OrderService;
import com.yangdonglin.mcto.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@RestController
@RequestMapping("/api/ordercomment")
public class OrderCommentController {

    @Resource
    private OrderCommentService orderCommentService;

    @Resource
    private OrderService orderService;

    @Resource
    private UserService userService;

    @PostMapping("/getCommentList")
    AjaxResponse getCommentList(){
        LambdaQueryWrapper<OrderComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderComment::getStatus,1)
                .orderByDesc(OrderComment::getCreateTime);
        List<OrderComment> result = orderCommentService.list(wrapper);
        return AjaxResponse.success(result);
    }

    @PostMapping("/addComment")
    AjaxResponse addComment(@RequestBody OrderComment param){
        OrderComment model = new OrderComment();
        model = param;
        Order orderModel = orderService.getById(param.getOrderId());
        orderModel.setStatus(-1);

        orderService.updateById(orderModel);

        User userModel = userService.getById(param.getUserId());
        model.setUserImg(userModel.getImg());
        model.setId(UUID.randomUUID().toString());
        model.setCreateTime(new Date());
        model.setStatus(1);
        boolean save = orderCommentService.save(model);
        if(save){
            return AjaxResponse.success();
        }
        return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_INSERT);
    }
}
