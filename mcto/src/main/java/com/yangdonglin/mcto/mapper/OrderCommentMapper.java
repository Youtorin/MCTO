package com.yangdonglin.mcto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangdonglin.mcto.entity.Food;
import com.yangdonglin.mcto.entity.OrderComment;
import org.apache.ibatis.annotations.Mapper;

/**

 * @author Yangdonglin
 * @since 2022-02-21
 */
@Mapper
public interface OrderCommentMapper extends BaseMapper<OrderComment> {
}
