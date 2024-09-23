package com.example.demo.service.external.impl;

import com.example.demo.service.external.ExternalService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExternalServiceImpl implements ExternalService {

    @Value("${app.main.service.external.url}")
    @Getter
    private String url;

    @Override
    public void callExternal() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String response = restTemplate.exchange(url + "200", HttpMethod.GET, entity, String.class).getBody();
            log.info("callExternal(): response={}", response);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
