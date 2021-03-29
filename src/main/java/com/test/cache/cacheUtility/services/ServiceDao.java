package com.test.cache.cacheUtility.services;

import com.test.cache.cacheUtility.model.Info;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class ServiceDao {

    public Info getServiceConfig(String svcConfigId){
        return new Info(Timestamp.from(Instant.now()).toString()+svcConfigId);
    }
}
