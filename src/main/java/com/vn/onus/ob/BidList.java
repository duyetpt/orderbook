package com.vn.onus.ob;

import com.vn.onus.order.BidOrder;
import com.vn.onus.treap.Treap;

import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class BidList extends OrderList<BidOrder> {
    public BidList(OrderBook.STRATEGY strategy) {
        switch (strategy) {
            case SINGLE_THREAD:
                this.list = new PriorityQueue<>();
            case SINGLE_THREAD_TREAP:
                this.list = new Treap<>();
            default:
                this.list = new PriorityBlockingQueue<>(11);
        }
    }
}
