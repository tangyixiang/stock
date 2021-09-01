package com.sky.stock.entity;

import com.sky.stock.util.IdUtils;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity(name = "american_stock")
public class AmericanStockInfo {

    @Id
    private String id;
    // 代码
    private String symbol;
    // 名称
    private String name;
    // 中文名称
    private String cname;
    // 分类
    private String category;
    // 价格
    private double price;
    // 高低位差值
    private double diff;
    // 涨跌额
    private double chg;
    // 昨收
    private double preclose;
    // 今开
    private double open;
    // 高位
    private double high;
    // 低位
    private double low;
    // 振幅
    private String amplitude;
    // 成交量
    private String volume;
    // 市值
    private long mktcap;
    // PE
    private String pe;
    // 市场类型
    private String market;
    // 分类ID
    private Integer category_id;
    // 日期
    private String detailDate;
    private LocalDateTime createTime = LocalDateTime.now();

}
