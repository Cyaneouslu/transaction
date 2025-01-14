package com.xiaolu.transaction.controller;

import com.xiaolu.transaction.entity.Transaction;
import com.xiaolu.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangxiaolu
 */
@RequestMapping("/transaction")
@RestController
public class TransactionManageController {

    @Autowired
    private TransactionService transactionService;

    /**
     * transaction create
     *
     * @param create the transaction need to create
     * @return the created transaction
     */
    @PostMapping("/create")
    public ResponseEntity<Transaction> create(@RequestBody Transaction create) {
        return new ResponseEntity<>(transactionService.create(create), HttpStatus.CREATED);
    }

    /**
     * transaction delete
     *
     * @param transactionId to be deleted transactionId
     * @return the deleted transaction
     */
    @DeleteMapping("/delete/{transactionId}")
    public ResponseEntity<Transaction> delete(@PathVariable String transactionId) {
        return new ResponseEntity<>(transactionService.delete(transactionId), HttpStatus.OK);
    }

    /**
     * transaction modify
     *
     * @param transactionId to be modified transactionId
     * @param modify        the transaction need to modify
     * @return the modified transaction
     */
    @PutMapping("/modify/{transactionId}")
    public ResponseEntity<Transaction> modify(@PathVariable String transactionId, @RequestBody Transaction modify) {
        modify.setTransactionId(transactionId);
        return new ResponseEntity<>(transactionService.modify(modify), HttpStatus.OK);
    }

    /**
     * list transaction for input account no
     *
     * @param pageIndex query page index,need to greater than 0, start from 1
     * @param pageSize  query page size,need to greater then 0
     * @param account   query account no ,mandatory
     * @return has no page info
     */
    @GetMapping("/list")
    public ResponseEntity<List<Transaction>> list(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                                  @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                  @RequestParam String account) {
        return new ResponseEntity<>(transactionService.list(pageIndex, pageSize, account), HttpStatus.OK);
    }
}
