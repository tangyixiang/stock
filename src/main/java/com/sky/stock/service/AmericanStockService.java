package com.sky.stock.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.stock.entity.AmericanStockInfo;
import com.sky.stock.param.SinaRequestParam;
import com.sky.stock.repository.AmericanStockRepository;
import com.sky.stock.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class AmericanStockService {

    private String sinaSource = "https://stock.finance.sina.com.cn/usstock/api/jsonp.php/IO.XSRV2.CallbackList['fa8Vo3U4TzVRdsLs']/US_CategoryService.getList";

    private String flagStr = "IO.XSRV2.CallbackList['fa8Vo3U4TzVRdsLs']";

    @Autowired
    private ThreadPoolTaskExecutor executor;
    @Autowired
    private AmericanStockRepository repository;

    public void run() {
        SinaRequestParam sinaRequestParam = new SinaRequestParam();

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(sinaRequestParam));
        Map<String, Object> paramMap = jsonObject;
        String url = RequestUtils.spliceUrl(sinaSource, paramMap);
        String result = HttpUtil.createPost(url).execute().body();



    }

    private void parseData(String responseData) {
        int jsonValueStart = responseData.indexOf(flagStr) + flagStr.length() + 1;
        String jsonValue = responseData.substring(jsonValueStart, responseData.length() - 2);
        String count = JSON.parseObject(jsonValue).getString("count");

        int total = Integer.parseInt(count);
        int loops = total % 60 == 0 ? total / 60 : total / 60 + 1;

        handleData(loops);
    }

    private void handleData(int loops){
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

    }
}
