package com.vn.onus;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    private BigDecimal price;
    private int orderAmount;
    private LocalDateTime createdDateTime;
    private String id;
    private OrderType orderType;
    private Currency baseCurrency;
    private Currency asset;
    private int bookedAmount;

    public int remainAmount() {
        return orderAmount - bookedAmount;
    }

    public void addBookedAmount(int val) {
        this.bookedAmount += val;
    }
}
