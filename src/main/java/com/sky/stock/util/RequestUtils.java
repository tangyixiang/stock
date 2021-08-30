package com.sky.stock.util;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class RequestUtils {

    public static String spliceUrl(String baseUrl, Map<String, Object> paramMap) {
        URL url = null;
        try {
            url = new URL(baseUrl);
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance().host(url.getHost()).path(url.getPath());
            paramMap.forEach((k, v) -> uriComponentsBuilder.queryParam(k, v));
            UriComponents uriComponents = uriComponentsBuilder.build();
            return uriComponents.toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
