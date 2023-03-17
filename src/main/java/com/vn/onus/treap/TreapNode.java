package com.vn.onus.treap;

import com.vn.onus.order.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TreapNode {
    long priority;
    String key;
    Order order;
    TreapNode left, right;

    public TreapNode(Order order) {
        this.priority = order.getPriority();
        this.key = order.getId();
        this.order = order;
    }
}
