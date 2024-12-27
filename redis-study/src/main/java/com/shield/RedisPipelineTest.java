package com.shield;

import com.shield.service.RedisPipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class RedisPipelineTest implements CommandLineRunner {
    @Autowired
    private RedisPipelineService redisPipelineService;

    public static void main(String[] args) {
        SpringApplication.run(RedisPipelineTest.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String,String> keyMap = new HashMap<>();
        keyMap.put("key1","value1");
        keyMap.put("key2","value2");
        redisPipelineService.deleteDataWithPipeline(keyMap.keySet());
        redisPipelineService.saveStringDataWithPipeline(keyMap, 5, TimeUnit.MINUTES);

        Map<String, List<String>> listMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("value1");
        list.add("value2");
        listMap.put("key3",list);
        List<String> list2 = new ArrayList<>();
        list2.add("value5");
        list2.add("value6");
        listMap.put("key4",list2);
        redisPipelineService.deleteDataWithPipeline(listMap.keySet());
        redisPipelineService.saveListDataWithPipeline(listMap,50000l);


        List<Object> stringDataWithPipeline = redisPipelineService.getStringDataWithPipeline(keyMap.keySet());
        System.out.println(stringDataWithPipeline);
        List<Object> multipleListsDataWithPipeline = redisPipelineService.getMultipleListsDataWithPipeline(listMap.keySet());
        System.out.println(multipleListsDataWithPipeline);
    }

}
