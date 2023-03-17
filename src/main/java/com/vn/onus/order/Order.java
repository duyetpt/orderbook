package com.vn.onus.order;

import com.vn.onus.Currency;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order implements IOrder {
    private BigDecimal price;
    private int orderAmount;
    private LocalDateTime createdDateTime;
    private String id;
    private Currency baseCurrency;
    private Currency asset;
    private int bookedAmount;

    public int remainAmount() {
        return orderAmount - bookedAmount;
    }

    public void addBookedAmount(int val) {
        this.bookedAmount += val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(Order o) {
        throw new UnsupportedOperationException("Please implement compareTo");
    }

    @Override
    public long getPriority() {
        throw new UnsupportedOperationException("Please implement getPriority");
    }
}
