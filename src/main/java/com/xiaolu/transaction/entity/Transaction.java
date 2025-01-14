package com.xiaolu.transaction.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wangxiaolu
 */
public class Transaction {
    /**
     * 交易ID
     */
    private String transactionId;
    /**
     * 交易账户
     */
    private String account;
    /**
     * 交易账户名
     */
    private String accountName;
    /**
     * 银行网点号
     */
    private String bankNo;
    /**
     * 交易类型
     */
    private String transactionType;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * 货币类型
     */
    private String currency;
    /**
     * 交易时间
     */
    private Date transactionTime;
    /**
     * 支付方式
     */
    private String payMethod;
    /**
     * 交易状态
     */
    private String status;
    /**
     * 交易备注
     */
    private String remark;
    /**
     * 目标账户
     */
    private String targetAccount;
    /**
     * 目标银行网点号
     */
    private String targetBankNo;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }

    public String getTargetBankNo() {
        return targetBankNo;
    }

    public void setTargetBankNo(String targetBankNo) {
        this.targetBankNo = targetBankNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
