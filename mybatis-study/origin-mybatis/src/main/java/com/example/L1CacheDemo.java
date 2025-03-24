package com.example;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class L1CacheDemo {
    public static void main(String[] args) {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            try (SqlSession session1 = sqlSessionFactory.openSession();
                 SqlSession session2 = sqlSessionFactory.openSession();
            ) {
                UserMapper mapper1 = session1.getMapper(UserMapper.class);
                UserMapper mapper2 = session2.getMapper(UserMapper.class);

                // Update user
                User user1 = mapper1.findUserById(1L);
                User user2 = mapper2.findUserById(1L);
                System.out.println("1. ===========" + user1.getUsername().equals(user2.getUsername()));

                user1.setUsername("new" + user1.getUsername());
                mapper1.updateUser(user1);
                User user3 = mapper1.findUserById(1L);
                System.out.println("2. ===========");

                User user4 = mapper2.findUserById(1L);
                System.out.println("3. ===========" + user3.getUsername().equals(user4.getUsername()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
