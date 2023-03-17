package com.vn.onus.ob;

import com.vn.onus.Order;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

public abstract class OrderList {
    protected PriorityBlockingQueue<Order> list;

    public Optional<Order> getBestOrder() {
        return list.isEmpty() ? Optional.empty() : Optional.of(list.poll());
    }

    public void addOrder(Order order) {
        list.add(order);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
