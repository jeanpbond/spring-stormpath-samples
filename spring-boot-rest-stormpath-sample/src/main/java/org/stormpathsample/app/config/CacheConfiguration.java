package org.stormpathsample.app.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        
        Set<Cache> caches = new HashSet<>();
        caches.add(spApplicationCache().getObject());
        caches.add(spAccountCache().getObject());
        caches.add(spGroupCache().getObject());
        caches.add(spCustomDataCache().getObject());
        cacheManager.setCaches(caches);
        
        return cacheManager;
    }
	
    @Bean 
    public ConcurrentMapCacheFactoryBean spApplicationCache() {
        ConcurrentMapCacheFactoryBean spApplicationCache = new ConcurrentMapCacheFactoryBean();
        spApplicationCache.setName("com.stormpath.sdk.application.Application");
        return spApplicationCache;
    }
	
    @Bean 
    public ConcurrentMapCacheFactoryBean spAccountCache() {
        ConcurrentMapCacheFactoryBean spAccountCache = new ConcurrentMapCacheFactoryBean();
        spAccountCache.setName("com.stormpath.sdk.account.Account");
        return spAccountCache;
    }
	
    @Bean 
    public ConcurrentMapCacheFactoryBean spGroupCache() {
    	ConcurrentMapCacheFactoryBean spGroupCache = new ConcurrentMapCacheFactoryBean();
    	spGroupCache.setName("com.stormpath.sdk.group.Group");
    	return spGroupCache;
    }
	
    @Bean 
    public ConcurrentMapCacheFactoryBean spCustomDataCache() {
    	ConcurrentMapCacheFactoryBean spCustomDataCache = new ConcurrentMapCacheFactoryBean();
    	spCustomDataCache.setName("com.stormpath.sdk.directory.CustomData");
    	return spCustomDataCache;
    }
}
