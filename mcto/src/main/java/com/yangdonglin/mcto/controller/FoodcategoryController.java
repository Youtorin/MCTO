package com.yangdonglin.mcto.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangdonglin.mcto.dto.IdDto;
import com.yangdonglin.mcto.entity.Foodcategory;
import com.yangdonglin.mcto.entity.User;
import com.yangdonglin.mcto.module.AjaxResponse;
import com.yangdonglin.mcto.module.BaseController;
import com.yangdonglin.mcto.module.PageQueryParams;
import com.yangdonglin.mcto.module.PageResult;
import com.yangdonglin.mcto.service.FoodcategoryService;
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
 * 食品分类表 前端控制器
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@RestController
@RequestMapping("/api//foodcategory")
public class FoodcategoryController extends BaseController {

    @Resource
    private FoodcategoryService cateService;

    @PostMapping("/getPageList")
    AjaxResponse getPageList(@RequestBody PageQueryParams params){
        QueryWrapper<Foodcategory> wrapper = new QueryWrapper<Foodcategory>();
        wrapper.eq("status", 1);
        if (StringUtils.isNotBlank(params.getKeywords())) {
            wrapper.like("name", params.getKeywords());
        }
        wrapper.orderByDesc("viewOrder");
        Page<Foodcategory> page = new Page<>();
        page = cateService.page(new Page<>(params.getPagination().getPage(), params.getPagination().getPageSize()), wrapper);
        PageResult<Foodcategory> result = PageResult.create(BeanCopyHelper.copyListProperties(page.getRecords(), Foodcategory::new), page.getTotal());
        if (ObjectUtils.isNotEmpty(result)) {
            return AjaxResponse.success(result);
        }
        return AjaxResponse.success(PageResult.create(new ArrayList<>(), page.getTotal()));
    }

    @PostMapping("/getList")
    AjaxResponse getList(){
        QueryWrapper<Foodcategory> wrapper = new QueryWrapper<Foodcategory>();
        wrapper.eq("status", 1)
                .orderByDesc("viewOrder");
        List<Foodcategory> result = cateService.list(wrapper);
        if (ObjectUtils.isNotEmpty(result)) {
            return AjaxResponse.success(result);
        }
        return AjaxResponse.success(PageResult.create(new ArrayList<>(), 0));
    }

    @PostMapping("/getModel")
    AjaxResponse getModel(@RequestBody IdDto id){
        Foodcategory model = cateService.getById(id.getId());
        if (ObjectUtils.isNotEmpty(model)) {
            return AjaxResponse.success(model);
        }
        return AjaxResponse.success(PageResult.create(new ArrayList<>(), 0));
    }

    @PostMapping("/delete")
    AjaxResponse delete(@RequestBody IdDto id) {
        Foodcategory model = cateService.getById(id.getId());
        model.setStatus(0);
        boolean bool = cateService.updateById(model);
        if (bool) {
            return AjaxResponse.success();
        }
        return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_DELETE);
    }

    @PostMapping("/edit")
    AjaxResponse edit(@RequestBody Foodcategory param) {
        Foodcategory model = cateService.getById(param.getId());
        if (ObjectUtils.isNotEmpty(model)) {
            model = param;
            boolean bool = cateService.updateById(model);
            if (bool) {
                return AjaxResponse.success();
            } else {
                return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_UPDATE);
            }
        } else {
            model = param;
            model.setStatus(1);
            model.setCreateTime(new Date());
            boolean bool = cateService.save(model);
            if (bool) {
                return AjaxResponse.success();
            } else {
                return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_UPDATE);
            }
        }
    }
}
