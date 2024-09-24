package com.example.demo.service.external.impl;

import com.example.demo.exception.InternalRetryException;
import com.example.demo.service.external.ExternalService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
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

    private final RestTemplate restTemplate;

    @Value("${app.main.service.external.url}")
    @Getter
    private String url;

    @Override
    @Retryable(include = InternalRetryException.class,
               maxAttemptsExpression = "${app.main.service.external.retry.max.attempts}",
               backoff = @Backoff(delayExpression = "${app.main.service.external.retry.max.delay}"))
    public void callExternal() throws InternalRetryException {
        log.info("callExternal(): retry={}", RetrySynchronizationManager.getContext().getRetryCount());
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String response = restTemplate.exchange(url + "200", HttpMethod.GET, entity, String.class).getBody();
            log.info("callExternal(): response={}", response);

        } catch (Exception e) {
            throw new InternalRetryException(e);
        }
    }
}
