package com.sky.stock.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class IdUtils {

    private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    public static String getId() {
        return snowflake.nextId() + "";
    }

}
