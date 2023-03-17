package com.vn.onus;

import com.vn.onus.ob.OrderBook;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class Exchange {
    private List<OrderBook> orderBooks = new ArrayList<>();

    public Optional<OrderBook> getOrderBook(Currency base, Currency asset) {
        return orderBooks.stream().filter(ob -> ob.getBase() == base && ob.getAsset() == asset).findFirst();
    }

    public void bid(Order order) {
        var bookOptional = getOrderBook(order.getBaseCurrency(), order.getAsset());
        if (bookOptional.isPresent()) {
            bookOptional.get().addBid(order);
        }
    }

    public void ask(Order order) {
        var bookOptional = getOrderBook(order.getBaseCurrency(), order.getAsset());
        if (bookOptional.isPresent()) {
            bookOptional.get().addAsk(order);
        }
    }

    /**
     * remove expired order
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Welcome to Onus exchange");

        var exchange = new Exchange();
        var ob = new OrderBook(Currency.USDT, Currency.BTC);
        exchange.orderBooks.add(ob);

        var random = new Random();
        new Runnable() {
            @Override
            public void run() {
                var startTime = System.currentTimeMillis();
                while ((System.currentTimeMillis() - startTime) <= 2000) {
                    var bidOrder = new Order();
                    bidOrder.setId(UUID.randomUUID().toString());
                    bidOrder.setOrderAmount(random.nextInt(100));
                    bidOrder.setPrice(new BigDecimal(random.nextInt(10_000)));
                    bidOrder.setOrderType(OrderType.BID);
                    bidOrder.setAsset(Currency.BTC);
                    bidOrder.setBaseCurrency(Currency.USDT);
                    bidOrder.setCreatedDateTime(LocalDateTime.now());
                    exchange.bid(bidOrder);

                    var ask = new Order();
                    ask.setId(UUID.randomUUID().toString());
                    ask.setOrderAmount(random.nextInt(100));
                    ask.setPrice(new BigDecimal(random.nextInt(10_000)));
                    ask.setOrderType(OrderType.ASK);
                    ask.setAsset(Currency.BTC);
                    ask.setBaseCurrency(Currency.USDT);
                    ask.setCreatedDateTime(LocalDateTime.now());
                    exchange.ask(ask);

                }
                System.out.println("Milliseconds: " + (System.currentTimeMillis() - startTime) + ", count: " + exchange.orderBooks.get(0).getPositions().size());
            }
        }.run();


    }
}
