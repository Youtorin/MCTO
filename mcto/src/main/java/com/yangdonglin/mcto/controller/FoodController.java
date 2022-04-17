package com.yangdonglin.mcto.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangdonglin.mcto.dto.IdDto;
import com.yangdonglin.mcto.entity.Food;
import com.yangdonglin.mcto.entity.Foodcategory;
import com.yangdonglin.mcto.module.AjaxResponse;
import com.yangdonglin.mcto.module.BaseController;
import com.yangdonglin.mcto.module.PageQueryParams;
import com.yangdonglin.mcto.module.PageResult;
import com.yangdonglin.mcto.service.FoodService;
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

/**
 * <p>
 * 菜品信息表 前端控制器
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@RestController
@RequestMapping("/api/food")
public class FoodController extends BaseController {

    @Resource
    private FoodService foodService;

    @PostMapping("/getList")
    AjaxResponse getList(@RequestBody PageQueryParams params){
        QueryWrapper<Food> wrapper = new QueryWrapper<Food>();
        wrapper.eq("status", 1);
        if (StringUtils.isNotBlank(params.getKeywords())) {
            wrapper.like("title", params.getKeywords());
        }
        if (StringUtils.isNotBlank(params.getPagination().getCateId())) {
            wrapper.eq("cateId", params.getPagination().getCateId());
        }
        wrapper.orderByDesc("catename");
        Page<Food> page = new Page<>();
        page = foodService.page(new Page<>(params.getPagination().getPage(), params.getPagination().getPageSize()), wrapper);
        PageResult<Food> result = PageResult.create(BeanCopyHelper.copyListProperties(page.getRecords(), Food::new), page.getTotal());
        if (ObjectUtils.isNotEmpty(result)) {
            return AjaxResponse.success(result);
        }
        return AjaxResponse.success(PageResult.create(new ArrayList<>(), page.getTotal()));
    }

    @PostMapping("/delete")
    AjaxResponse delete(@RequestBody List<IdDto> ids) {
        List<Food> list = new ArrayList<>();
        for (IdDto id : ids) {
            Food model = foodService.getById(id.getId());
            model.setStatus(0);
            list.add(model);
        }
        boolean bool = foodService.updateBatchById(list);
        if (bool) {
            return AjaxResponse.success();
        }
        return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_DELETE);
    }

    @PostMapping("/edit")
    AjaxResponse edit(@RequestBody Food param) {
        Food model = foodService.getById(param.getId());
        if (ObjectUtils.isNotEmpty(model)) {
            model = param;
            boolean bool = foodService.updateById(model);
            if (bool) {
                return AjaxResponse.success();
            } else {
                return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_UPDATE);
            }
        } else {
            model = param;
            model.setStatus(1);
            model.setCreateTime(new Date());
            boolean bool = foodService.save(model);
            if (bool) {
                return AjaxResponse.success();
            } else {
                return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_UPDATE);
            }
        }
    }
}
