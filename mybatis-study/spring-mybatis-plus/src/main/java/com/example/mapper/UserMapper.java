package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;

public interface UserMapper extends BaseMapper<User> {
    // 继承 BaseMapper 已包含基本CRUD方法
}
