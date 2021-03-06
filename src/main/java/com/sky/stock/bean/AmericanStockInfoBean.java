package com.sky.stock.bean;

import com.sky.stock.util.IdUtils;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class AmericanStockInfoBean {

    private String name;
    private String cname;
    private String category;
    private String symbol;
    private String price;
    private String diff;
    private String chg;
    private String preclose;
    private String open;
    private String high;
    private String low;
    private String amplitude;
    private String volume;
    private String mktcap;
    private String pe;
    private String market;
    private String category_id;

}
