package com.vn.onus.ob;

import com.vn.onus.AskComparator;
import com.vn.onus.ob.OrderList;

import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class AskList extends OrderList {
    public AskList() {
        this.list = new PriorityBlockingQueue<>(11, new AskComparator());
    }
}
