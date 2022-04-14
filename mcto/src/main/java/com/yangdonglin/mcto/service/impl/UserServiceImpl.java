package com.yangdonglin.mcto.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangdonglin.mcto.entity.User;
import com.yangdonglin.mcto.mapper.UserMapper;
import com.yangdonglin.mcto.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
