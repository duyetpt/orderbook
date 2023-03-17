package com.vn.onus;

import com.vn.onus.ob.OrderBook;
import com.vn.onus.order.AskOrder;
import com.vn.onus.order.BidOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class Exchange {
    private List<OrderBook> orderBooks = new ArrayList<>();

    public Optional<OrderBook> getOrderBook(Currency base, Currency asset) {
        return orderBooks.stream().filter(ob -> ob.getBase() == base && ob.getAsset() == asset).findFirst();
    }

    public void bid(BidOrder order) {
        var bookOptional = getOrderBook(order.getBaseCurrency(), order.getAsset());
        if (bookOptional.isPresent()) {
            bookOptional.get().addBid(order);
        }
    }

    public void ask(AskOrder order) {
        var bookOptional = getOrderBook(order.getBaseCurrency(), order.getAsset());
        if (bookOptional.isPresent()) {
            bookOptional.get().addAsk(order);
        }
    }

    public void cancelBid(BidOrder order) {
        var bookOptional = getOrderBook(order.getBaseCurrency(), order.getAsset());
        if (bookOptional.isPresent()) {
            bookOptional.get().cancelBid(order);
        }
    }

    public void cancelAsk(AskOrder order) {
        var bookOptional = getOrderBook(order.getBaseCurrency(), order.getAsset());
        if (bookOptional.isPresent()) {
            bookOptional.get().cancelAsk(order);
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
        var ob = new OrderBook(Currency.USDT, Currency.BTC, OrderBook.STRATEGY.SINGLE_THREAD);
        exchange.orderBooks.add(ob);

        var random = new Random();
        var runner = new Runnable() {
            @Override
            public void run() {
                var startTime = System.currentTimeMillis();
                int i = 0;
                int iCancellation = 0;
                while ((System.currentTimeMillis() - startTime) <= 1_000) {
                    var bidOrder = new BidOrder();
                    bidOrder.setId(UUID.randomUUID().toString());
                    bidOrder.setOrderAmount(random.nextInt(100));
                    bidOrder.setPrice(new BigDecimal(random.nextInt(10_000)));
                    bidOrder.setAsset(Currency.BTC);
                    bidOrder.setBaseCurrency(Currency.USDT);
                    bidOrder.setCreatedDateTime(LocalDateTime.now());
                    exchange.bid(bidOrder);
                    if (i%20 == 7) {
                        exchange.cancelBid(bidOrder);
                        iCancellation += 1;
                    }

                    var ask = new AskOrder();
                    ask.setId(UUID.randomUUID().toString());
                    ask.setOrderAmount(random.nextInt(100));
                    ask.setPrice(new BigDecimal(random.nextInt(10_000)));
                    ask.setAsset(Currency.BTC);
                    ask.setBaseCurrency(Currency.USDT);
                    ask.setCreatedDateTime(LocalDateTime.now());
                    exchange.ask(ask);
                    if (i%16 == 7) {
                        exchange.cancelAsk(ask);
                        iCancellation += 1;
                    }
                    i += 1;
                }
                System.out.println("Milliseconds: " + (System.currentTimeMillis() - startTime) + ", orders: " + i +
                        ", cancellation: " + iCancellation +
                        ", positions: " + exchange.orderBooks.get(0).getPositions().size());
            }
        };

        var thread = new Thread(runner);
        System.out.println("=== Thread safe mode ===");
        thread.run();

        System.out.println("=== None thread safe mode ===");
        ob = new OrderBook(Currency.USDT, Currency.BTC, OrderBook.STRATEGY.SINGLE_THREAD);
        exchange.orderBooks.clear();
        exchange.orderBooks.add(ob);
        thread.run();

        System.out.println("=== None thread safe mode treap ===");
        ob = new OrderBook(Currency.USDT, Currency.BTC, OrderBook.STRATEGY.SINGLE_THREAD_TREAP);
        exchange.orderBooks.clear();
        exchange.orderBooks.add(ob);
        thread.run();
    }
}
