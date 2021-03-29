package com.test.cache.cacheUtility.controller;

import com.test.cache.cacheUtility.model.Info;
import com.test.cache.cacheUtility.services.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController()
public class CacheUtilityController {

    @Autowired
    private CacheService cacheService;

    @GetMapping("/getData")
    public String getFromCache(@RequestParam String value) throws ExecutionException {
        Info info = cacheService.getServiceConfig(value);
        return info==null?"":info.getRandomNum();
    }

    @GetMapping("/getClear")
    public void clear() throws ExecutionException {
        cacheService.clearCache();
    }

    @GetMapping("/getMap")
    public Map<String, Info> getFr0mMap() throws ExecutionException {
        return cacheService.getCachedServiceConfigs();
    }

}
