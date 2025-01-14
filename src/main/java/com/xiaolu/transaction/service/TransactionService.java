package com.xiaolu.transaction.service;

import com.xiaolu.transaction.entity.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction create(Transaction create);

    Transaction delete(String transactionId);

    Transaction modify(Transaction modify);

    List<Transaction> list(Integer pageIndex, Integer pageSize, String account);
}
