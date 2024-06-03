package com.example.springstudy.dynamicproperties;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    //@Bean
    //public ThreadPoolExecutor testPool() {
    //    ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 20, 3L,
    //            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>()
    //            , new NamedThreadFactory("testPool"), new ThreadPoolExecutor.AbortPolicy());
    //    executor.setThreadPoolName("testPool");
    //    executor.setCorePoolSize(5);
    //    executor.setMaximumPoolSize(20);
    //    return executor;
    //}
    //
    //
    //@Bean
    //public DynamicThreadPoolPostProcessor dynamicThreadPoolPostProcessor() {
    //    return new DynamicThreadPoolPostProcessor();
    //}

    @Bean
    public TestBean testBean() {
        TestBean testBean = new TestBean();
        testBean.setName("test_name");
        return testBean;
    }

    @Data
    public static class TestBean{
        private String name;
    }
}
