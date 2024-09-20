package com.example.demo.conf;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class NodeConfiguration {

    private String nodeUuid;

    @PostConstruct
    public void init() {
        nodeUuid = UUID.randomUUID().toString();
        log.info("node uuid: {}", nodeUuid);
    }

    public String getNodeUuid() {
        return nodeUuid;
    }
}
