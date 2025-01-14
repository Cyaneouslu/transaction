package com.xiaolu.transaction.service.impl;

import com.xiaolu.transaction.config.BusinessException;
import com.xiaolu.transaction.config.SystemException;
import com.xiaolu.transaction.entity.Transaction;
import com.xiaolu.transaction.service.SimpleCacheService;
import com.xiaolu.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author wangxiaolu
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private static ConcurrentHashMap<String, Transaction> transactionMap = new ConcurrentHashMap<>();

    @Autowired
    private SimpleCacheService simpleCacheService;

    @Override
    public Transaction create(Transaction create) {
        String transactionId = create.getTransactionId();
        try {
            String account = create.getAccount();
            BigDecimal amount = create.getAmount();
            if (!StringUtils.hasText(transactionId) || !StringUtils.hasText(account) || Objects.isNull(amount)) {
                throw new BusinessException("params lack");
            }
            if (checkExist(transactionId)) {
                throw new BusinessException("transaction exist:" + transactionId);
            }
            Date now = new Date();
            create.setCreateTime(now);
            create.setUpdateTime(now);
            log.info("start create transaction:{}", transactionId);
            transactionMap.put(transactionId, create);
            log.info("end create transaction:{}", transactionId);
            simpleCacheService.clearCache(create.getAccount());
            return create;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("create error,transactionId:{}", transactionId, e);
            throw new SystemException("system busy");
        }
    }

    @Override
    public Transaction delete(String transactionId) {
        try {
            if (!checkExist(transactionId)) {
                throw new BusinessException("transaction not exist:" + transactionId);
            }
            log.info("start delete transaction:{}", transactionId);
            Transaction remove = transactionMap.remove(transactionId);
            log.info("end delete transaction:{}", transactionId);
            simpleCacheService.clearCache(remove.getAccount());
            return remove;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("delete error,transactionId:{}", transactionId, e);
            throw new SystemException("system busy");
        }
    }

    @Override
    public Transaction modify(Transaction modify) {
        String transactionId = modify.getTransactionId();
        try {
            if (!checkExist(transactionId)) {
                throw new BusinessException("transaction not exist:" + transactionId);
            }
            Transaction old = transactionMap.get(transactionId);
            if (!old.getAccount().equals(modify.getAccount())) {
                throw new BusinessException("can not modify account:" + transactionId);
            }
            Date now = new Date();
            modify.setCreateTime(now);
            modify.setUpdateTime(now);
            log.info("start modify transaction:{}", transactionId);
            transactionMap.put(transactionId, modify);
            log.info("end modify transaction:{}", transactionId);
            simpleCacheService.clearCache(modify.getAccount());
            return modify;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("modify error,transactionId:{}", transactionId, e);
            throw new SystemException("system busy");
        }
    }

    private boolean checkExist(String transactionId) {
        if (!StringUtils.hasText(transactionId)) {
            throw new BusinessException("transactionId lack");
        }
        Transaction transaction = transactionMap.get(transactionId);
        return Objects.nonNull(transaction);
    }

    @Override
    public List<Transaction> list(Integer pageIndex, Integer pageSize, String account) {
        try {
            if (pageIndex <= 0 || pageSize <= 0 || !StringUtils.hasText(account)) {
                throw new BusinessException("params illegal");
            }
            String cacheKey = pageIndex + "#" + pageSize + "#" + account;
            List<Transaction> cache = simpleCacheService.getCache(cacheKey);
            if (Objects.nonNull(cache)) {
                log.info("find in cache,pageIndex:{},pageSize:{},account:{}", pageIndex, pageSize, account);
                return cache;
            }
            log.info("start query transaction,pageIndex:{},pageSize:{},account:{}", pageIndex, pageSize, account);
            List<Transaction> result = transactionMap.values().stream()
                    .filter(transaction -> transaction.getAccount().equals(account))
                    .sorted(Comparator.comparing(Transaction::getTransactionId).reversed())
                    .skip((long) (pageIndex - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
            log.info("end query transaction,pageIndex:{},pageSize:{},account:{},result size:{}", pageIndex, pageSize, account, result.size());
            simpleCacheService.setCache(cacheKey, result);
            return result;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("list query error,account:{}", account, e);
            throw new SystemException("system busy");
        }
    }
}
