package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;

public interface UserMapper extends BaseMapper<User> {
    // 继承 BaseMapper 即拥有 CRUD 方法
}