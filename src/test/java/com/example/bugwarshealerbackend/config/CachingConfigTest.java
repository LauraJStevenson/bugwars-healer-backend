package com.example.bugwarshealerbackend.config;

import org.junit.jupiter.api.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class CachingConfigTest {

    @Test
    void cacheManager() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(CachingConfig.class);
        context.refresh();

        CacheManager cacheManager = context.getBean(CacheManager.class);
        assertNotNull(cacheManager);

        Cache usersCache = cacheManager.getCache("users");
        assertNotNull(usersCache);

        assertTrue(usersCache instanceof ConcurrentMapCache);
    }
}