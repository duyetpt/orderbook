package com.vn.onus.order;

import com.vn.onus.ComparingResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class BidOrder extends Order {
    @Override
    public long getPriority() {
        return (this.getPrice().multiply(new BigDecimal(1000)).longValue() * 10_000 +
                getCreatedDateTime().toEpochSecond(ZoneOffset.UTC)) -
                LocalDateTime.now().toLocalDate().toEpochSecond(LocalTime.MIN, ZoneOffset.UTC);
    }

    @Override
    public int compareTo(Order o2) {
        var priceComparing = o2.getPrice().compareTo(this.getPrice());
        if (ComparingResult.isEqual(priceComparing)) {
            var timeComparing = this.getCreatedDateTime().compareTo(o2.getCreatedDateTime());
            return ComparingResult.isEqual(timeComparing) ? this.remainAmount() - o2.remainAmount() : timeComparing;
        }
        return priceComparing;
    }
}
