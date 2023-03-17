package com.vn.onus.order;

public interface IOrder extends Comparable<Order> {
    long getPriority();
}
