package com.yangdonglin.mcto.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangdonglin.mcto.entity.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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



    @PostMapping("/getList")
    AjaxResponse getList(@RequestBody PageQueryParams params) {
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();
        if (StringUtils.isNotBlank(params.getKeywords())) {
            wrapper.and(p -> {
                p.like("username", params.getKeywords())
                        .or().like("userMobile", params.getKeywords())
                        .or().like("userMobile", params.getKeywords())
                        .or().like("food", params.getKeywords());
            });
        }
        wrapper.orderByDesc("catename");
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
        List<Food> foodList = (List<Food>) JSON.parseArray(param.getFood(),Food.class);
        Order order = param;
        //获取用户信息
        User userModel = userService.getById(order.getUserId());
        order.setUserMobile(userModel.getMobile());
        order.setUsername(userModel.getUsername());

        //获取地址信息
        UserAddress userAddressModel = userAddressService.getById(order.getUserAddressId());
        order.setUserAddress(userAddressModel.getAddress());

        //获取商铺信息
        Shop shopModel = shopService.getById(foodList.get(0).getShopId());
        LambdaQueryWrapper <Shopinfo> shopinfoWrapper = new LambdaQueryWrapper<>();
        shopinfoWrapper.eq(Shopinfo::getShopId,shopModel.getId());
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
            return AjaxResponse.success();
        }
        return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_SAVE);
    }
}
