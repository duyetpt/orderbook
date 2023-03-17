package com.vn.onus.ob;

import com.vn.onus.*;
import com.vn.onus.order.AskOrder;
import com.vn.onus.order.BidOrder;
import com.vn.onus.order.Order;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Data
public class OrderBook {

    public static enum STRATEGY {
        SINGLE_THREAD,
        THREAD_SAFE,
        SINGLE_THREAD_TREAP,
    }

    private BidList bidList;
    private AskList askList;
    private List<Position> positions;

    private Currency base;
    private Currency asset;
    private STRATEGY strategy;

    public OrderBook(Currency base, Currency asset, STRATEGY strategy) {
        this.base = base;
        this.asset = asset;
        this.bidList = new BidList(strategy);
        this.askList = new AskList(strategy);
        if (strategy != null || strategy == STRATEGY.SINGLE_THREAD || strategy == STRATEGY.SINGLE_THREAD_TREAP) {
            this.positions = new ArrayList<>();
        } else {
            this.positions = new Vector<>();
            this.strategy = STRATEGY.THREAD_SAFE;
        }
    }

    public void addBid(BidOrder bid) {
        bidList.addOrder(bid);
        if (!askList.isEmpty()) fill();
    }

    public void addAsk(AskOrder ask) {
        askList.addOrder(ask);
        if (!bidList.isEmpty()) fill();
    }

    public void cancelBid(BidOrder order) {
        bidList.removeOrder(order);
    }

    public void cancelAsk(AskOrder order) {
        askList.removeOrder(order);
    }

    public Position fill() {
        var bestBid = bidList.getBestOrder().get();
        var bestAsk = askList.getBestOrder().get();

        if (bestBid.getPrice().compareTo(bestAsk.getPrice()) >= 0) {
            var amount = Math.min(bestAsk.remainAmount(), bestBid.remainAmount());
            var price = bestAsk.getPrice();
            var position = new Position();
            position.setAskOrder(bestAsk);
            position.setBidOrder(bestBid);
            position.setAmount(amount);
            position.setPrice(price);
            position.setCreatedDateTime(LocalDateTime.now());
//            adjust bid, ask
            bestAsk.addBookedAmount(amount);
            bestBid.addBookedAmount(amount);
            if (bestAsk.remainAmount() > 0) {
                addAsk(bestAsk);
            }
            if (bestBid.remainAmount() > 0) {
                addBid(bestBid);
            }
            positions.add(position);
            return position;
        }

        return null;
    }
}
