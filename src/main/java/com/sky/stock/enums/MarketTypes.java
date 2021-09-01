package com.sky.stock.enums;

public enum MarketTypes {

    US("US"), CHINA("CHINA"), HK("HK");

    String name;

    MarketTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
