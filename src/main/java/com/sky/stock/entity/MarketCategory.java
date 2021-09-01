package com.sky.stock.entity;

import lombok.Data;

import javax.persistence.Id;

@Data
public class MarketCategory {

    @Id
    private String id;

    private String categoryId;

    private String categoryName;

    private String marketType;

}
