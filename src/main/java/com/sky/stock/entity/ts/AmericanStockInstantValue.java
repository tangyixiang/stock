package com.sky.stock.entity.ts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmericanStockInstantValue {

    private long timestamp;

    private String symbol;

    private String category;

    private double price;

}
