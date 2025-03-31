package com.example.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
@Slf4j
public class DatabaseShardingAlgorithm implements StandardShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        long userId = preciseShardingValue.getValue();

        //如何需要新增加库，那么根据新的userId，走心的逻辑进行路由
        //if (userId >= 1000000) {
        //    String database = "m" + ((userId % 2) + 3);  //// m3 或 m4
        //    log.info("routing key : {} , target database : {}", userId, database);
        //    return database;
        //}

        String database = "m" + ((userId % 2) + 1); // m1 或 m2
        log.info("routing key : {} , target database : {}", userId, database);
        return database;
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        return Collections.emptyList();
    }

    @Override
    public void init() {

    }

    @Override
    public String getType() {
        return "";
    }
}
