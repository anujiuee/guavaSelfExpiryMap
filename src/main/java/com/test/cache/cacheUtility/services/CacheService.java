package com.test.cache.cacheUtility.services;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.test.cache.cacheUtility.model.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    @Autowired
    private ServiceDao servicesDao;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    Cache<String, Info> serviceConfigCache = CacheBuilder.newBuilder()
            .expireAfterWrite(20, TimeUnit.SECONDS).build();


    public Info getServiceConfig(String svcConfigId) throws ExecutionException {
       if (serviceConfigCache.getIfPresent(svcConfigId) == null) {
            Info esbServiceConfig = servicesDao.getServiceConfig(svcConfigId);
            if (esbServiceConfig != null) {
                taskExecutor.submit(()-> serviceConfigCache.put(svcConfigId, esbServiceConfig));
                return esbServiceConfig;
            }
        }
        return serviceConfigCache.getIfPresent(svcConfigId);
      /*  return serviceConfigCache.get(svcConfigId, new Callable<Info>() {
            @Override
            public Info call() throws Exception {
                return servicesDao.getServiceConfig(svcConfigId);
            }
        });

       */
    }

    public List<Info> getServiceConfigs(List<String> svcConfigIds) throws Exception {
        List<Info> esbServiceConfigs = new ArrayList<>();
        for (String svcConfigId : svcConfigIds) {
            Info esbServiceConfig = getServiceConfig(svcConfigId);
            if (esbServiceConfig == null) {
                throw new Exception(
                        "Service Config for svcConfigId: " + svcConfigId + " not found");
            }
            esbServiceConfigs.add(esbServiceConfig);
        }
        return esbServiceConfigs;
    }

    public void clearCache() {
        serviceConfigCache.invalidateAll();
    }

    public Map<String, Info> getCachedServiceConfigs() {
        return serviceConfigCache.asMap();
    }
}
