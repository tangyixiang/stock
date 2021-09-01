package com.sky.stock.listener;

import com.sky.stock.bean.AmericanStockInfoBean;
import com.sky.stock.entity.AmericanStockInfo;
import com.sky.stock.entity.MarketCategory;
import com.sky.stock.entity.ts.AmericanStockInstantValue;
import com.sky.stock.enums.MarketTypes;
import com.sky.stock.event.AmericanStockEvent;
import com.sky.stock.repository.AmericanStockRepository;
import com.sky.stock.util.LocalDateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class AmericanStockListener {

    private AmericanStockRepository repository;

    @Async
    @EventListener
    public void americanListener(AmericanStockEvent americanStockEvent) {
        List<AmericanStockInfoBean> americanStockInfoBeans = americanStockEvent.getAmericanStockInfoBeans();
        List<AmericanStockInfo> list = new ArrayList<>();
        List<MarketCategory> categoryList = new ArrayList<>();
        for (AmericanStockInfoBean americanStockInfoBean : americanStockInfoBeans) {
            covertTsValue(americanStockInfoBean);
            if (!"0".equals(americanStockInfoBean.getVolume()))
                list.add(covertDateValue(americanStockInfoBean, categoryList));
        }

        repository.saveAll(list);
    }


    private void covertTsValue(AmericanStockInfoBean americanStockInfoBean) {
        String symbol = americanStockInfoBean.getSymbol();
        String category = americanStockInfoBean.getCategory();
        Double price = Double.valueOf(americanStockInfoBean.getPrice());
        long timestamp = LocalDateUtil.getAmericanDateTime();
        AmericanStockInstantValue americanStockInstantValue = new AmericanStockInstantValue(timestamp, symbol, category, price);
    }

    /**
     * 转换成每日数据
     *
     * @param americanStockInfoBean
     * @param categoryList
     * @return
     */
    private AmericanStockInfo covertDateValue(AmericanStockInfoBean americanStockInfoBean, List<MarketCategory> categoryList) {
        AmericanStockInfo americanStockInfo = new AmericanStockInfo();
        americanStockInfo.setDetailDate(LocalDateUtil.getAmericanDate());
        try {
            PropertyUtils.copyProperties(americanStockInfo, americanStockInfoBean);
            if (!StringUtils.isEmpty(americanStockInfo.getCategory())) {
                MarketCategory marketCategory = new MarketCategory();
                marketCategory.setCategoryId(americanStockInfo.getCategory_id() + "");
                marketCategory.setCategoryName(americanStockInfo.getCategory());
                marketCategory.setMarketType(MarketTypes.US.getName());
                categoryList.add(marketCategory);
            }

        } catch (Exception e) {
            log.error("AmericanStockInfoBean 对象转换失败,错误原因:{}", e);
        }

        return americanStockInfo;
    }


}
