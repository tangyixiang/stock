package com.sky.stock;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.sky.stock.entity.AmericanStockInfo;
import com.sky.stock.repository.AmericanStockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SpringBootTest
class StockApplicationTests {

    @Autowired
    private AmericanStockRepository repository;

    @Test
    void contextLoads() throws InterruptedException {
        String flagStr = "IO.XSRV2.CallbackList['fa8Vo3U4TzVRdsLs']";
        String url = "https://stock.finance.sina.com.cn/usstock/api/jsonp.php/IO.XSRV2.CallbackList['fa8Vo3U4TzVRdsLs']/US_CategoryService.getList?" +
                "page=%d&num=60&sort=&asc=0&market=&id=";
        int total = 0;
        int loops = 1;

        String result = HttpUtil.createPost(String.format(url, 1)).execute().body();
        int jsonValueStart = result.indexOf(flagStr) + flagStr.length() + 1;
        String jsonValue = result.substring(jsonValueStart, result.length() - 2);
        String count = JSON.parseObject(jsonValue).getString("count");
        total = Integer.parseInt(count);
        loops = total % 60 == 0 ? total / 60 : total / 60 + 1;

        List<CompletableFuture<Integer>> list = new ArrayList<>();
        for (int i = 1; i <= loops; i++) {
            int finalI = i;
            CompletableFuture<Integer> data = CompletableFuture.supplyAsync(() -> {
                String body = HttpUtil.createPost(String.format(url, finalI)).execute().body();
                int start = body.indexOf(flagStr) + flagStr.length() + 1;
                String value = body.substring(start, body.length() - 2);
                List<AmericanStockInfo> americanStockInfos = JSON.parseArray(JSON.parseObject(value).getString("data"), AmericanStockInfo.class);
                repository.saveAll(americanStockInfos);
                return finalI;
            });
            list.add(data);

        }

        CompletableFuture<Void> future = CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]));
        future.join();

        System.out.println("完成");
    }

}


