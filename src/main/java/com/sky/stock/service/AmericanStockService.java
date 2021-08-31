package com.sky.stock.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.stock.entity.AmericanStockInfo;
import com.sky.stock.event.AmericanStockEvent;
import com.sky.stock.param.SinaRequestParam;
import com.sky.stock.repository.AmericanStockRepository;
import com.sky.stock.util.LocalDateUtil;
import com.sky.stock.util.RequestUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class AmericanStockService {

    private String sinaSource = "https://stock.finance.sina.com.cn/usstock/api/jsonp.php/IO.XSRV2.CallbackList['fa8Vo3U4TzVRdsLs']/US_CategoryService.getList";

    private String flagStr = "IO.XSRV2.CallbackList['fa8Vo3U4TzVRdsLs']";

    private ThreadPoolTaskExecutor executor;

    private AmericanStockRepository repository;

    private ApplicationEventPublisher publisher;

    public void run() {
        SinaRequestParam sinaRequestParam = new SinaRequestParam();
        Map<String, Object> paramMap = objectToMap(sinaRequestParam);
        String url = RequestUtils.spliceUrl(sinaSource, paramMap);
        String result = HttpUtil.createPost(url).execute().body();
        result = parseDataToJsonString(result);
        String count = JSON.parseObject(result).getString("count");
        int total = Integer.parseInt(count);
        int loops = total % 60 == 0 ? total / 60 : total / 60 + 1;
        handleData(loops);
    }

    private void handleData(int loops) {
        String americanDate = LocalDateUtil.getAmericanDate();
        for (int i = 1; i <= loops; i++) {
            int finalI = i;
            executor.execute(() -> {
                Map<String, Object> paramMap = objectToMap(new SinaRequestParam(finalI));
                String url = RequestUtils.spliceUrl(sinaSource, paramMap);
                String result = HttpUtil.createPost(url).execute().body();
                result = parseDataToJsonString(result);
                List<AmericanStockInfo> americanStockInfos = JSON.parseArray(JSON.parseObject(result).getString("data"), AmericanStockInfo.class);
                americanStockInfos.forEach(americanStockInfo -> americanStockInfo.setDetailDate(americanDate));
                publisher.publishEvent(new AmericanStockEvent(this, americanStockInfos));
                repository.saveAll(americanStockInfos);
            });
        }
    }

    private Map<String, Object> objectToMap(SinaRequestParam sinaRequestParam) {
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(sinaRequestParam));
        Map<String, Object> paramMap = (Map<String, Object>) jsonObject;
        return paramMap;
    }

    private String parseDataToJsonString(String responseData) {
        int jsonValueStart = responseData.indexOf(flagStr) + flagStr.length() + 1;
        String jsonValue = responseData.substring(jsonValueStart, responseData.length() - 2);
        return jsonValue;
    }

}
