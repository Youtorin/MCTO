package com.yangdonglin.mcto.service.impl;

import com.yangdonglin.mcto.entity.Food;
import com.yangdonglin.mcto.mapper.FoodMapper;
import com.yangdonglin.mcto.service.FoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品信息表 服务实现类
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {

}
