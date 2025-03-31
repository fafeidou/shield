package com.example;

import com.example.mapper.OrderDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SpringBootTest
class OrderTest {

    @Resource
    OrderDao orderDao;

    @Test
    public void testInsertOrder() {
        Random random = new Random(100);
        for (int i = 1; i < 20; i++) {
            orderDao.insertOrder(new BigDecimal(i), (long) i + random.nextInt(100), "SUCCESS");

        }
    }

    @Test
    public void testSelectOrderByIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(884877870038515712L);
        ids.add(373897037306920961L);

        List<Map> maps = orderDao.selectOrderByIds(ids);
        System.out.println(maps);
    }
}