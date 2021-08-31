package com.sky.stock.event;

import com.sky.stock.entity.AmericanStockInfo;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class AmericanStockEvent extends ApplicationEvent {

    private List<AmericanStockInfo> americanStockInfos;

    public AmericanStockEvent(Object source) {
        super(source);
    }

    public AmericanStockEvent(Object source, List<AmericanStockInfo> americanStockInfos) {
        super(source);
        this.americanStockInfos = americanStockInfos;
    }

    public List<AmericanStockInfo> getAmericanStockInfos() {
        return americanStockInfos;
    }
}
