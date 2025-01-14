package com.xiaolu.transaction.service.impl;

import com.xiaolu.transaction.entity.Transaction;
import com.xiaolu.transaction.service.SimpleCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SimpleCacheServiceImpl implements SimpleCacheService {
    private static final Logger log = LoggerFactory.getLogger(SimpleCacheServiceImpl.class);

    private static ConcurrentHashMap<String, List<Transaction>> cache = new ConcurrentHashMap<>();


    @Override
    public void setCache(String key, List<Transaction> transactionList) {
        log.info("set cache,key:{}", key);
        cache.put(key, transactionList);
    }

    @Override
    public List<Transaction> getCache(String key) {
        return cache.get(key);
    }

    @Override
    public void clearCache(String account) {
        log.info("clear cache,account:{}", account);
        ConcurrentHashMap.KeySetView<String, List<Transaction>> keys = cache.keySet();
        for (String key : keys) {
            if (key.endsWith(account)) {
                cache.remove(key);
            }
        }
    }
}
