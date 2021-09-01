package com.sky.stock.event;

import com.sky.stock.bean.AmericanStockInfoBean;
import com.sky.stock.entity.AmericanStockInfo;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class AmericanStockEvent extends ApplicationEvent {

    private List<AmericanStockInfoBean> americanStockInfoBeans;

    public AmericanStockEvent(Object source) {
        super(source);
    }

    public AmericanStockEvent(Object source, List<AmericanStockInfoBean> americanStockInfoBeans) {
        super(source);
        this.americanStockInfoBeans = americanStockInfoBeans;
    }

    public List<AmericanStockInfoBean> getAmericanStockInfoBeans() {
        return americanStockInfoBeans;
    }
}
