package com.sky.stock.entity;

import com.sky.stock.util.IdUtils;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "american_stock")
public class AmericanStockInfo {


    @Id
    private String id;
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

    public AmericanStockInfo() {
        this.id = IdUtils.getId();
    }

}
