package com.yangdonglin.mcto.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangdonglin.mcto.dto.IdDto;
import com.yangdonglin.mcto.entity.UserAddress;
import com.yangdonglin.mcto.module.AjaxResponse;
import com.yangdonglin.mcto.module.BaseController;
import com.yangdonglin.mcto.module.PageResult;
import com.yangdonglin.mcto.service.UserAddressService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户配送地址表 前端控制器
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@RestController
@CrossOrigin(allowCredentials="true")
@RequestMapping("/api/userAddress")
public class UserAddressController extends BaseController {

    @Resource
    UserAddressService userAddressService;

    @PostMapping("/getList")
    public AjaxResponse getList(@RequestBody IdDto userId){
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("userid",userId.getId())
                .eq("status",1)
                .orderByDesc("createtime");
        List<UserAddress> result = userAddressService.list(wrapper);
        if(ObjectUtils.isNotEmpty(result)){
            System.out.println(result);
            return AjaxResponse.success(result);
        }else {
            return AjaxResponse.success(PageResult.create(new ArrayList<>(), 0));
        }
    }

    @PostMapping("/delete")
    AjaxResponse delete(@RequestBody IdDto id) {
        UserAddress model = userAddressService.getById(id.getId());
        model.setStatus(0);
        boolean bool = userAddressService.updateById(model);
        if (bool) {
            return AjaxResponse.success();
        }
        return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_DELETE);
    }

    @PostMapping("/edit")
    AjaxResponse edit(@RequestBody UserAddress param) {
        UserAddress model = userAddressService.getById(param.getId());
        if (ObjectUtils.isNotEmpty(model)) {
            model = param;
            boolean bool = userAddressService.updateById(model);
            if (bool) {
                return AjaxResponse.success();
            } else {
                return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_UPDATE);
            }
        } else {
            model = param;
            model.setStatus(1);
            model.setCreateTime(new Date());
            boolean bool = userAddressService.save(model);
            if (bool) {
                return AjaxResponse.success();
            } else {
                return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_UPDATE);
            }
        }
    }

}
