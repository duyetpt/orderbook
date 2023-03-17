package com.vn.onus;

import com.vn.onus.order.Order;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Position {
    private Order bidOrder;
    private Order askOrder;
    private int amount;
    private BigDecimal price;
    private LocalDateTime createdDateTime;

    @Override
    public String toString() {
        return "Position{" +
                "bidOrder=" + bidOrder +
                ", askOrder=" + askOrder +
                ", amount=" + amount +
                ", price=" + price +
                ", createdDateTime=" + createdDateTime.toString() +
                '}';
    }
}
