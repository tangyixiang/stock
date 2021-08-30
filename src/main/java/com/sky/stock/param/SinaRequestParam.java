package com.sky.stock.param;

import lombok.Data;

@Data
public class SinaRequestParam {

    /*
        page=%d&num=60&sort=&asc=0&market=&id=
    */

    // 默认第一页
    private int page = 1;
    // 默认分页大小60
    private int num = 60;

    private String sort;

    private int asc;

    private String market;

    private String id;

}
