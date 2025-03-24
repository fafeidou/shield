package com.example.mapper;


import com.example.entity.User;

import java.util.List;

public interface UserMapper {
    List<User> findAllUsers();

    User findUserById(Long id);

    void insertUser(User user);

    void updateUser(User user);

    void deleteUser(Long id);
}
