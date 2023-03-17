package com.vn.onus.ob;

import com.vn.onus.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderBook {
    private BidList bidList;
    private AskList askList;
    private List<Position> positions;

    private Currency base;
    private Currency asset;

    public OrderBook(Currency base, Currency asset) {
        this.base = base;
        this.asset = asset;
        this.bidList = new BidList();
        this.askList = new AskList();
        this.positions = new ArrayList<>();
    }

    public void addBid(Order bid) {
        bidList.addOrder(bid);
        if (!askList.isEmpty()) fill();
    }

    public void addAsk(Order ask) {
        askList.addOrder(ask);
        if (!bidList.isEmpty()) fill();
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
