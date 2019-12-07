package com.example.grabgrid.Model;

import java.io.Serializable;

public class Transaction implements Serializable {

    private int txnId;
    private int userId;
    private String service;
    private int amount;

    public Transaction() { }

    public Transaction(int txnId, int userId, String service, int amount) {
        this.txnId = txnId;
        this.userId = userId;
        this.service = service;
        this.amount = amount;
    }

    public int getTxnId() {
        return txnId;
    }

    public void setTxnId(int txnId) {
        this.txnId = txnId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "txnId=" + txnId +
                ", userId=" + userId +
                ", service='" + service + '\'' +
                ", amount=" + amount +
                '}';
    }
}