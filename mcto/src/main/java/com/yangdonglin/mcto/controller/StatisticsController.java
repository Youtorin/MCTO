package com.yangdonglin.mcto.controller;

import com.alibaba.fastjson.JSON;
import com.yangdonglin.mcto.entity.Statistics;
import com.yangdonglin.mcto.mapper.StatisticsMapper;
import com.yangdonglin.mcto.module.AjaxResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 统计接口
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Resource
    private StatisticsMapper statisticsMapper;

    @PostMapping("/getStatistics")
    AjaxResponse getStatistics(){
        Statistics model = new Statistics();
        model.setOrderByCate(JSON.toJSONString(statisticsMapper.getOrderByCate()));
        model.setFoodAndCount(JSON.toJSONString(statisticsMapper.getFoodAndCount()));
        model.setTotalCate(statisticsMapper.getTotalCate());
        model.setTotalComment(statisticsMapper.getTotalComment());
        model.setTotalSales(statisticsMapper.getTotalSales());
        model.setTotalUser(statisticsMapper.getTotalUser());
        model.setTotalView(statisticsMapper.getTotalView());
        return AjaxResponse.success(model);
    }
}
