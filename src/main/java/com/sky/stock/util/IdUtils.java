package com.sky.stock.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class IdUtils {

    private static final Snowflake snowflake = IdUtil.getSnowflake(8, 8);

    public static String getId() {
        return snowflake.nextId() + "";
    }

    public static void main(String[] args) {
        System.out.println(getId());
    }

}
