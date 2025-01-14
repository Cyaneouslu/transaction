package com.xiaolu.transaction.service;

import com.xiaolu.transaction.entity.Transaction;

import java.util.List;

public interface SimpleCacheService {

    void setCache(String key, List<Transaction> transactionList);

    List<Transaction> getCache(String key);

    void clearCache(String account);
}
