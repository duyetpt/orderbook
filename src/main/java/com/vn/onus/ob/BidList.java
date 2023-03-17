package com.vn.onus.ob;

import com.vn.onus.BidComparator;

import java.util.concurrent.PriorityBlockingQueue;

public class BidList extends OrderList {
    public BidList() {
        this.list = new PriorityBlockingQueue<>(11, new BidComparator());
    }
}
