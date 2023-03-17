package com.vn.onus.order;

import com.vn.onus.ComparingResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class AskOrder extends Order {
    @Override
    public long getPriority() {
        return (100_000_000_000l - (getPrice().multiply(new BigDecimal(1000)).longValue()) * 10_000 +
                getCreatedDateTime().toEpochSecond(ZoneOffset.UTC)) -
                LocalDateTime.now().toLocalDate().toEpochSecond(LocalTime.MIN, ZoneOffset.UTC);
    }

    @Override
    public int compareTo(Order order) {
        var priceComparing = this.getPrice().compareTo(order.getPrice());
        if (ComparingResult.isEqual(priceComparing)) {
            var timeComparing = this.getCreatedDateTime().compareTo(order.getCreatedDateTime());
            return ComparingResult.isEqual(timeComparing) ? this.remainAmount() - order.remainAmount() : timeComparing;
        }
        return priceComparing;
    }
}
