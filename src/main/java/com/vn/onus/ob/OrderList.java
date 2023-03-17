package com.vn.onus.ob;

import com.vn.onus.order.Order;

import java.util.Optional;
import java.util.Queue;

public abstract class OrderList<T extends Order> {
    protected Queue<T> list;

    public Optional<T> getBestOrder() {
        return list.isEmpty() ? Optional.empty() : Optional.of(list.poll());
    }

    public void addOrder(T order) {
        list.add(order);
    }

    public void removeOrder(T order) {
        list.remove(order);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
