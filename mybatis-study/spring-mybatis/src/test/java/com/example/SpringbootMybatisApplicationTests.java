package com.example;

import com.example.entity.User;
import com.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootMybatisApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void testCRUD() {

        List<User> allUsers = userService.getAllUsers();

        // 测试插入
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        int insertCount = userService.createUser(user);
        Assertions.assertEquals(1, insertCount);

        // 测试查询
        User dbUser = userService.getUserById(user.getId());
        Assertions.assertNotNull(dbUser);
        Assertions.assertEquals("testUser", dbUser.getUsername());

        // 测试更新
        user.setEmail("new@example.com");
        int updateCount = userService.updateUser(user);
        Assertions.assertEquals(1, updateCount);

        // 测试删除
        int deleteCount = userService.deleteUser(user.getId());
        Assertions.assertEquals(1, deleteCount);
    }
}
