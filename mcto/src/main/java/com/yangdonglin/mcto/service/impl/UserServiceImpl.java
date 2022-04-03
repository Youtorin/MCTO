package com.yangdonglin.mcto.service.impl;

import com.yangdonglin.mcto.entity.User;
import com.yangdonglin.mcto.mapper.UserMapper;
import com.yangdonglin.mcto.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录表 服务实现类
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
