package com.sky.stock.listener;

import com.sky.stock.entity.AmericanStockInfo;
import com.sky.stock.entity.ts.AmericanStockInstantValue;
import com.sky.stock.event.AmericanStockEvent;
import com.sky.stock.util.LocalDateUtil;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockListener {

    @Async
    @EventListener
    public void americanListener(AmericanStockEvent americanStockEvent) {
        List<AmericanStockInfo> americanStockInfos = americanStockEvent.getAmericanStockInfos();
        for (AmericanStockInfo americanStockInfo : americanStockInfos) {
            String symbol = americanStockInfo.getSymbol();
            String category = americanStockInfo.getCategory();
            Double price = Double.valueOf(americanStockInfo.getPrice());
            long timestamp = LocalDateUtil.getAmericanDateTime();
            AmericanStockInstantValue americanStockInstantValue = new AmericanStockInstantValue(timestamp, symbol, category, price);

        }
    }

}
