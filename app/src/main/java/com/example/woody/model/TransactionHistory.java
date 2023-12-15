package com.example.woody.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class TransactionHistory implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("amount")
    @Expose
    private int amount;
    @SerializedName("bankCode")
    @Expose
    private String bankCode;
    @SerializedName("bankTranNo")
    @Expose
    private String bankTranNo;
    @SerializedName("orderInfor")
    @Expose
    private String orderInfor;
    @SerializedName("cardType")
    @Expose
    private String cardType;
    @SerializedName("payDate")
    @Expose
    private Date payDate;
    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("tmnCode")
    @Expose
    private String tmnCode;
    @SerializedName("transactionStatus")
    @Expose
    private String transactionStatus;
    @SerializedName("transactionNo")
    @Expose
    private String transactionNo;

    public TransactionHistory() {
    }

    public TransactionHistory(int id, int amount, String bankCode, String bankTranNo, String orderInfor, String cardType, Date payDate, String responseCode, String tmnCode, String transactionStatus, String transactionNo) {
        this.id = id;
        this.amount = amount;
        this.bankCode = bankCode;
        this.bankTranNo = bankTranNo;
        this.orderInfor = orderInfor;
        this.cardType = cardType;
        this.payDate = payDate;
        this.responseCode = responseCode;
        this.tmnCode = tmnCode;
        this.transactionStatus = transactionStatus;
        this.transactionNo = transactionNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankTranNo() {
        return bankTranNo;
    }

    public void setBankTranNo(String bankTranNo) {
        this.bankTranNo = bankTranNo;
    }

    public String getOrderInfor() {
        return orderInfor;
    }

    public void setOrderInfor(String orderInfor) {
        this.orderInfor = orderInfor;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getTmnCode() {
        return tmnCode;
    }

    public void setTmnCode(String tmnCode) {
        this.tmnCode = tmnCode;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }
}
