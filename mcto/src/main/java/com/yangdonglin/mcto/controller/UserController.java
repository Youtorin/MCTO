package com.yangdonglin.mcto.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangdonglin.mcto.dto.IdDto;
import com.yangdonglin.mcto.entity.User;
import com.yangdonglin.mcto.module.AjaxResponse;
import com.yangdonglin.mcto.module.PageQueryParams;
import com.yangdonglin.mcto.module.PageResult;
import com.yangdonglin.mcto.service.UserService;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userServicel;

    @PostMapping("/getList")
    AjaxResponse getList(@RequestBody PageQueryParams params) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("status", 1);
        if (StringUtils.isNotBlank(params.getKeywords())) {
            wrapper.like("username", params.getKeywords())
                    .or().like("truename", params.getKeywords())
                    .or().like("email", params.getKeywords());
        }
        wrapper.orderByDesc("createtime");
        Page<User> page = new Page<>();
        page = userServicel.page(
                new Page<>(
                        params.getPagination().getPage(),
                        params.getPagination().getPageSize()),
                wrapper);
        PageResult<User> result = PageResult.create(
                BeanCopyHelper.copyListProperties(page.getRecords(), User::new),
                page.getTotal());
        if (ObjectUtils.isNotEmpty(result)) {
            return AjaxResponse.success(result);
        }
        return AjaxResponse.success(PageResult.create(new ArrayList<>(), page.getTotal()));
    }

    @PostMapping("/delete")
    AjaxResponse delete(@RequestBody IdDto id) {
        User model = userServicel.getById(id.getId());
        model.setStatus(0);
        boolean bool = userServicel.updateById(model);
        if (bool) {
            return AjaxResponse.success();
        }
        return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_DELETE);
    }

    @PostMapping("/edit")
    AjaxResponse edit(@RequestBody User param) {
        User model = userServicel.getById(param.getId());
        if (ObjectUtils.isNotEmpty(model)) {
            model = param;
            boolean bool = userServicel.updateById(model);
            if (bool) {
                return AjaxResponse.success();
            } else {
                return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_UPDATE);
            }
        } else {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getUsername,param.getUsername());
            User user = userServicel.getOne(wrapper);
            if(ObjectUtils.isNotEmpty(user)){
                return AjaxResponse.error("账号已存在");
            }
            model = param;
            model.setStatus(1);
            model.setCreateTime(new Date());
            model.setId(UUID.randomUUID().toString());
            boolean bool = userServicel.save(model);
            if (bool) {
                return AjaxResponse.success();
            } else {
                return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_UPDATE);
            }
        }
    }

    @PostMapping("/getModel")
    AjaxResponse getModel(@RequestBody IdDto id) {
        User model = userServicel.getById(id.getId());
        if (ObjectUtils.isNotEmpty(model)) {
            return AjaxResponse.success(model);
        }
        return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_NOT_EXITS);
    }

    @PostMapping("/getCurrUserInfo")
    AjaxResponse getModel(@RequestBody User param) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,param.getUsername())
                .eq(User::getPassword,param.getPassword());
        User model = userServicel.getOne(wrapper);
        if (ObjectUtils.isNotEmpty(model)) {
            return AjaxResponse.success(model);
        }
        return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_NOT_EXITS);
    }

    @PostMapping("/editWallet")
    AjaxResponse editWallet(@RequestBody User param){
        User model = userServicel.getById(param.getId());
        model.setWallet(model.getWallet().add(param.getWallet()));
        boolean bool = userServicel.updateById(model);
        if (bool) {
            return AjaxResponse.success(model.getWallet());
        }
        return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_UPDATE);
    }
}
