package com.example.proguard;

import com.example.proguard.test.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1. 首先执行 mvn pacackage
 * 2. 执行 java -jar proguard-0.0.1.jar ,获取混淆之后的堆栈
 * 3. 执行 com.example.proguard.test.SymbolAnalysis 可以观察到被解析之后异常堆栈
 */
@SpringBootApplication
public class ProguardApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProguardApplication.class, args);
    }

    @Override
    public void run(String... args) {
        User user = new User();
        user.setId("q2");
        user.setName("sdf");
    }
}
