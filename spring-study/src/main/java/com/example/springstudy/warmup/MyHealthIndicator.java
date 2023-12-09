package com.example.springstudy.warmup;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * http://localhost:8080/actuator/health
 * 服务启动后，服务健康状态为DOWN，等待执行完成warmup()之后变为UP
 *
 * @author batman
 */
@Component
public class MyHealthIndicator implements HealthIndicator {
    public static final AtomicBoolean preheat = new AtomicBoolean(false);

    public static final AtomicBoolean preheatEnd = new AtomicBoolean(false);

    public static final AtomicInteger count = new AtomicInteger(0);

    @Override
    public Health health() {
        if (preheat.compareAndSet(false, true)) {
            warmup();
            preheatEnd.compareAndSet(false, true);
        }

        if (!preheatEnd.get()) {
            return Health.down().build();
        }

        return Health.up().build();
    }

    private void warmup() {

        try {
            Thread.sleep(5000L
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
