package com.example.woody.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class UsedService implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("dateStart")
    @Expose
    private String dateStart;
    @SerializedName("dateEnd")
    @Expose
    private String dateEnd;
    @SerializedName("typePayment")
    @Expose
    private String typePayment;
    @SerializedName("service")
    @Expose
    private Service service;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tranHistory")
    @Expose
    private TransactionHistory tranHistory;

    private Voucher voucher;
    private int totalMoney;

    public UsedService() {
    }

    public UsedService(int id, String dateStart, String dateEnd, String typePayment, Service service, User user, String status, TransactionHistory tranHistory, Voucher voucher, int totalMoney) {
        this.id = id;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.typePayment = typePayment;
        this.service = service;
        this.user = user;
        this.status = status;
        this.tranHistory = tranHistory;
        this.voucher=voucher;
        this.totalMoney=totalMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getTypePayment() {
        return typePayment;
    }

    public void setTypePayment(String typePayment) {
        this.typePayment = typePayment;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TransactionHistory getTranHistory() {
        return tranHistory;
    }

    public void setTranHistory(TransactionHistory tranHistory) {
        this.tranHistory = tranHistory;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }
}
