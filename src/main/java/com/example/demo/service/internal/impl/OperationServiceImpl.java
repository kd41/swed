package com.example.demo.service.internal.impl;

import com.example.demo.exception.InternalPreconditionFailedException;
import com.example.demo.exception.InternalRollBackableException;
import com.example.demo.model.entity.ClientAccountEntity;
import com.example.demo.repository.ClientAccountRepository;
import com.example.demo.service.internal.OperationService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class OperationServiceImpl implements OperationService {
    private final ClientAccountRepository clientAccountRepository;

    @Modifying
    @Transactional(rollbackFor = { InternalRollBackableException.class })
    @Override
    public boolean add(int clientAccountId, int amount) throws InternalRollBackableException {
        log.info("add(): clientAccountId={}, amount={}", clientAccountId, amount);
        try {
            ClientAccountEntity clientAccountEntity = clientAccountRepository.lockById(clientAccountId);
            clientAccountEntity.setAmount(clientAccountEntity.getAmount() + amount);
            clientAccountRepository.save(clientAccountEntity);
            log.info("add(): Success");
            return true;
        } catch (Exception e) {
            log.error("can't add", e);
            throw new InternalRollBackableException();
        }
    }

    @Modifying
    @Transactional(rollbackFor = { InternalRollBackableException.class })
    @Override
    public boolean debit(int clientAccountId, int amount) throws InternalRollBackableException, InternalPreconditionFailedException {
        log.info("debit(): clientAccountId={}, amount={}", clientAccountId, amount);
        try {
            ClientAccountEntity clientAccountEntity = clientAccountRepository.lockById(clientAccountId);
            if (clientAccountEntity.getAmount() - amount < 0) {
                throw new InternalPreconditionFailedException("Not enough amount on clientAccountId=" + clientAccountId + " to debit amount=" + amount);
            }
            clientAccountEntity.setAmount(clientAccountEntity.getAmount() - amount);
            clientAccountRepository.save(clientAccountEntity);
            log.info("debit(): Success");
            return true;
        } catch (InternalPreconditionFailedException e) {
            throw e;
        } catch (Exception e) {
            log.error("can't debit", e);
            throw new InternalRollBackableException();
        }
    }
}
