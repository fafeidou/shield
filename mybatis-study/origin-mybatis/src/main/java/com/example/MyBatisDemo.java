package com.example;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisDemo {
    public static void main(String[] args) {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            try (SqlSession session = sqlSessionFactory.openSession()) {
                UserMapper mapper = session.getMapper(UserMapper.class);

                // Insert user
                //User newUser = new User();
                //newUser.setUsername("john_doe");
                //newUser.setEmail("john@example.com");
                //mapper.insertUser(newUser);
                //session.commit();

                // Query users
                //List<User> users = mapper.findAllUsers();
                //users.forEach(System.out::println);

                // Update user
                User userToUpdate = mapper.findUserById(1L);
                userToUpdate.setEmail("new.email@example.com");
                //mapper.updateUser(userToUpdate);
                //测试一级缓存需要注释掉，测试二级缓存需要打开
                //session.commit();
                System.out.println("=====================");
                User userById = mapper.findUserById(1L);

                // Delete user
                //mapper.deleteUser(2L);
                //session.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
