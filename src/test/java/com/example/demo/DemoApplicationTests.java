package com.example.demo;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
                properties = {
                    "local.server.port=8080"
                })
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DemoApplicationTests {

    private static final String DEPOSIT_URL = "http://localhost:8080/api/client-account/deposit/1";
    private static final String WITHDRAW_URL = "http://localhost:8080/api/client-account/withdraw/1";

    @LocalServerPort
    private int randomServerPort = 8080;

    private RestTemplate restTemplate;

    @BeforeAll
    void init() {
        restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(10000);
        restTemplate.setRequestFactory(requestFactory);
    }

    @Test
    void depositTest(String currency) {
        send(true, currency);
    }

    @Test
    void withdrawTest(String currency) {
        send(false, currency);
    }

    @Test
    void depositParallel1000Test() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(1);
        }
        list.stream().parallel().forEach(c -> {
            depositTest("eur");
        });
    }

    @Test
    void withdrawParallel1000Test() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(1);
        }
        list.stream().parallel().forEach(c -> {
            withdrawTest("eur");
        });
    }

    @Test
    void withdrawParallel1000Test2() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Callable<Void> c = () -> {
                withdrawTest("eur");
                return null;
            };
            tasks.add(c);
        }
        executorService.invokeAll(tasks);
    }

    @Test
    void withdrawParallel1000Test3() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            if (i % 2 == 0) {
                Callable<Void> c = () -> {
                    withdrawTest("eur");
                    return null;
                };
                tasks.add(c);
            } else {
                Callable<Void> c = () -> {
                    withdrawTest("usd");
                    return null;
                };
                tasks.add(c);
            }
        }
        executorService.invokeAll(tasks);
    }

    private void send(boolean deposit, String currency) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", 1);
            jsonObject.put("currency", currency);

            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

            String url = DEPOSIT_URL;
            if (!deposit) {
                url = WITHDRAW_URL;
            }

            String response = restTemplate.patchForObject(url, entity, String.class);
            log.info("contextLoads(): response={}", response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
