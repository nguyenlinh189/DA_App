package com.example.woody.model;

import java.io.Serializable;

public class Voucher implements Serializable {
    private String code;
    private String dateStart;
    private String dateEnd;
    private int amount;

    public Voucher() {
    }

    public Voucher(String code, String dateStart, String dateEnd, int amount) {
        this.code = code;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
