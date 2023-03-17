package com.vn.onus;

import java.util.Comparator;

public class AskComparator implements Comparator<Order> {
    final int EQUAL_VAL = 0;

    @Override
    public int compare(Order o1, Order o2) {
        var priceComparing = o1.getPrice().compareTo(o2.getPrice());
        if (priceComparing == EQUAL_VAL) {
            var timeComparing = o1.getCreatedDateTime().compareTo(o2.getCreatedDateTime());
            return timeComparing == EQUAL_VAL ? o1.remainAmount() - o2.remainAmount() : timeComparing;
        }
        return priceComparing;
    }
}
