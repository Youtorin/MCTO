package com.yangdonglin.mcto.mapper;

import com.yangdonglin.mcto.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户登录表 Mapper 接口
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
