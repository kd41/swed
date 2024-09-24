package com.example.demo.service.internal.impl;

import com.example.demo.exception.InternalPreconditionFailedException;
import com.example.demo.service.external.ExternalService;
import com.example.demo.service.internal.OperationService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("operationFacadeService")
@Slf4j
@AllArgsConstructor
public class OperationFacadeServiceImpl implements OperationService {

    private final ExternalService externalService;
    private final OperationService operationService;

    @Override
    public boolean add(int clientAccountId, int amount) {
        return operationService.add(clientAccountId, amount);
    }

    @Override
    public boolean debit(int clientAccountId, int amount) throws InternalPreconditionFailedException {
        beforeProcessing();
        try {
            return operationService.debit(clientAccountId, amount);
        } finally {
            afterProcessing();
        }

    }

    @Override
    public boolean exchange(int clientAccountFromId, int clientAccountToId, int amount) throws InternalPreconditionFailedException {
        return operationService.exchange(clientAccountFromId, clientAccountToId, amount);
    }

    private void beforeProcessing() {
        try {
            externalService.callExternal();
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void afterProcessing() {
    }
}
