package com.yangdonglin.mcto.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangdonglin.mcto.dto.IdDto;
import com.yangdonglin.mcto.dto.OrderDto;
import com.yangdonglin.mcto.entity.*;
import com.yangdonglin.mcto.mapper.OrderMapper;
import com.yangdonglin.mcto.module.AjaxResponse;
import com.yangdonglin.mcto.module.BaseController;
import com.yangdonglin.mcto.module.PageQueryParams;
import com.yangdonglin.mcto.module.PageResult;
import com.yangdonglin.mcto.service.*;
import com.yangdonglin.mcto.utils.BeanCopyHelper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 订单主表 前端控制器
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@RestController
@RequestMapping("/api/order")
public class OrderController extends BaseController {

    @Resource
    private OrderService orderService;

    @Resource
    private UserService userService;

    @Resource
    private ShopinfoService shopinfoService;

    @Resource
    private ShopService shopService;

    @Resource
    private UserAddressService userAddressService;

    @Resource
    private OrderMapper orderMapper;


    @PostMapping("/getList")
    AjaxResponse getList(@RequestBody PageQueryParams params) {
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();
        if (ObjectUtils.isNotEmpty(params.getStatus())) {
            wrapper.eq("status", params.getStatus());
        }
        if (StringUtils.isNotBlank(params.getKeywords())) {
            wrapper.and(p -> {
                p.like("username", params.getKeywords())
                        .or().like("userMobile", params.getKeywords())
                        .or().like("userMobile", params.getKeywords())
                        .or().like("food", params.getKeywords());
            });
        }
        wrapper.orderByDesc("createtime");
        Page<Order> page = new Page<>();
        page = orderService.page(new Page<>(params.getPagination().getPage(), params.getPagination().getPageSize()), wrapper);
        PageResult<Order> result = PageResult.create(BeanCopyHelper.copyListProperties(page.getRecords(), Order::new), page.getTotal());
        if (ObjectUtils.isNotEmpty(result)) {
            return AjaxResponse.success(result);
        }
        return AjaxResponse.success(PageResult.create(new ArrayList<>(), page.getTotal()));
    }

    @PostMapping("/getListByUser")
    AjaxResponse getListByUser(@RequestBody PageQueryParams params) {
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();
        if (ObjectUtils.isNotEmpty(params.getStatus())) {
            wrapper.eq("status", params.getStatus());
        }
        if (ObjectUtils.isNotEmpty(params.getUserId())) {
            wrapper.eq("userid", params.getUserId());
        }
        if (StringUtils.isNotBlank(params.getKeywords())) {
            wrapper.and(p -> {
                p.like("username", params.getKeywords())
                        .or().like("userMobile", params.getKeywords())
                        .or().like("userMobile", params.getKeywords())
                        .or().like("food", params.getKeywords());
            });
        }
        wrapper.orderByDesc("createtime");
        Page<Order> page = new Page<>();
        page = orderService.page(new Page<>(params.getPagination().getPage(), params.getPagination().getPageSize()), wrapper);
        PageResult<Order> result = PageResult.create(BeanCopyHelper.copyListProperties(page.getRecords(), Order::new), page.getTotal());
        if (ObjectUtils.isNotEmpty(result)) {
            return AjaxResponse.success(result);
        }
        return AjaxResponse.success(PageResult.create(new ArrayList<>(), page.getTotal()));
    }

    @PostMapping("/addOrder")
    AjaxResponse addOrder(@RequestBody Order param) {
        List<Food> foodList = (List<Food>) JSON.parseArray(param.getFood(), Food.class);
        Order order = param;

        //获取地址信息
        UserAddress userAddressModel = userAddressService.getById(order.getUserAddressId());
        order.setUserAddress(userAddressModel.getAddress());

        //获取商铺信息
        Shop shopModel = shopService.getById(foodList.get(0).getShopId());
        LambdaQueryWrapper<Shopinfo> shopinfoWrapper = new LambdaQueryWrapper<>();
        shopinfoWrapper.eq(Shopinfo::getShopId, shopModel.getId());
        Shopinfo shopinfoModel = shopinfoService.getOne(shopinfoWrapper);
        order.setShopId(shopinfoModel.getShopId());
        order.setShopname(shopinfoModel.getShopname());
        order.setShopMobile(shopinfoModel.getMobile());
        order.setShopAddress(shopinfoModel.getProvince() + shopinfoModel.getCity() + shopinfoModel.getDistrict() + shopinfoModel.getAddress());

        order.setId(UUID.randomUUID().toString());
        order.setCreateTime(new Date());
        order.setStatus(1);
        boolean bool = orderService.save(order);
        if (bool) {
            return AjaxResponse.success(order.getId());
        }
        return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_SAVE);
    }


    @PostMapping("/setOrderStatus")
    AjaxResponse setOrderStatus(@RequestBody OrderDto param) {
        Order orderModel = orderService.getById(param.getId());
        //当订单创建成功时
        if ("2".equals(param.getPayType())) {
            User userModel = userService.getById(orderModel.getUserId());
            BigDecimal wallet = userModel.getWallet();
            if (wallet.compareTo(param.getPrice()) != -1) {
                BigDecimal subtract = wallet.subtract(param.getPrice());
                userModel.setWallet(subtract);
                userService.updateById(userModel);
            } else {
                return AjaxResponse.error("钱包余额不足");
            }
        }
        orderModel.setStatus(param.getStatus());
        boolean bool = orderService.updateById(orderModel);
        if (bool) {
            return AjaxResponse.success();
        }
        return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_SAVE);
    }

    @PostMapping("/selectCount")
    AjaxResponse selectCount(@RequestBody IdDto idDto) {
        List<HashMap<Integer, Integer>> result = orderMapper.selectCount(idDto.getId());
        if (ObjectUtils.isNotEmpty(result)) {
            return AjaxResponse.success(result);
        }
        return AjaxResponse.success();
    }

    /**
     * 退款申请
     */
    @PostMapping("/refund")
    AjaxResponse orderRefund(@RequestBody Order param) {
        Order model = orderService.getById(param.getId());
        if (ObjectUtils.isNotEmpty(model)) {
            model = param;
            model.setApplyTime(new Date());
            model.setPayMoney(model.getTotalMoney());
            model.setStatus(5);
            model.setRefundStatus(1);
        } else {
            return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_NOT_EXITS);
        }
        boolean bool = orderService.updateById(model);
        if (bool) {
            return AjaxResponse.success();
        }
        return AjaxResponse.error("系统异常");
    }

    /**
     * 退款
     */
    @PostMapping("/handlerRefund")
    AjaxResponse handlerRefund(@RequestBody Order param) {
        Order model = orderService.getById(param.getId());
        model = param;
        model.setFinishTime(new Date());
        model.setStatus(-1);
        model.setRefundStatus(2);

        //获取用户信息-退回款项
        User userModel = userService.getById(model.getUserId());
        userModel.setWallet(userModel.getWallet().add(param.getPayMoney()));
        userService.updateById(userModel);

        boolean bool = orderService.updateById(model);
        if (bool) {
            return AjaxResponse.success();
        }
        return AjaxResponse.error("系统异常");
    }


    /**
     * 拒绝
     */
    @PostMapping("/handlerCancel")
    AjaxResponse handlerCancel(@RequestBody Order param) {
        Order model = orderService.getById(param.getId());
        model = param;
        model.setFinishTime(new Date());
        model.setStatus(-1);
        model.setRefundStatus(3);

        boolean bool = orderService.updateById(model);
        if (bool) {
            return AjaxResponse.success();
        }
        return AjaxResponse.error("系统异常");
    }
}
