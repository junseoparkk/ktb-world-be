package com.singtanglab.ktbworld.config;

import java.time.Duration;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // 프록시 설정
        HttpHost proxy = new HttpHost("krmp-proxy.9rum.cc", 3128);

        // HttpClient 생성 및 프록시 적용
        HttpClient httpClient = HttpClientBuilder.create()
                .setProxy(proxy)
                .build();

        // HttpComponentsClientHttpRequestFactory에 HttpClient 설정
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        // RestTemplateBuilder를 사용하여 RestTemplate 구성
        return builder
                .requestFactory(() -> factory)
                .setConnectTimeout(Duration.ofSeconds(10)) // 연결 타임아웃 설정
                .setReadTimeout(Duration.ofSeconds(10)) // 읽기 타임아웃 설정
                .build();
    }
}
