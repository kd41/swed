package com.example.demo.service.internal.impl;

import com.example.demo.exception.InternalPreconditionFailedException;
import com.example.demo.exception.InternalRollBackableException;
import com.example.demo.model.entity.ClientAccountEntity;
import com.example.demo.model.entity.CurrencyEntity;
import com.example.demo.model.entity.CurrencyExchangeEntity;
import com.example.demo.repository.ClientAccountRepository;
import com.example.demo.repository.CurrencyExchangeRepository;
import com.example.demo.repository.CurrencyRepository;
import com.example.demo.service.internal.OperationService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("operationService")
@Slf4j
@AllArgsConstructor
public class OperationServiceImpl implements OperationService {
    private final ClientAccountRepository clientAccountRepository;
    private final CurrencyExchangeRepository currencyExchangeRepository;
    private final CurrencyRepository currencyRepository;

    @Modifying
    @Transactional
    @Override
    public boolean add(int clientAccountId, int amount) {
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
    @Transactional
    @Override
    public boolean debit(int clientAccountId, int amount) throws InternalPreconditionFailedException {
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

    @Modifying
    @Transactional
    @Override
    public boolean exchange(int fromClientAccountId, int toClientAccountId, int amount) throws InternalPreconditionFailedException {
        log.info("exchange(): fromClientAccountId={}, toClientAccountId={}, amount={}", fromClientAccountId, toClientAccountId, amount);
        try {

            ClientAccountEntity fromClientAccountEntity = clientAccountRepository.lockById(fromClientAccountId);
            if (fromClientAccountEntity.getAmount() - amount < 0) {
                throw new InternalPreconditionFailedException(
                    "Not enough amount on fromClientAccountId=" + fromClientAccountEntity + " to debit amount=" + amount);
            }
            ClientAccountEntity toClientAccountEntity = clientAccountRepository.lockById(toClientAccountId);
            Optional<CurrencyExchangeEntity> currencyExchangeEntityOptional =
                currencyExchangeRepository.findFirstByFromCurrencyIdAndToCurrencyId(fromClientAccountEntity.getCurrencyId(),
                                                                                    toClientAccountEntity.getCurrencyId());
            if (!currencyExchangeEntityOptional.isPresent()) {
                CurrencyEntity fromCurrencyEntity = currencyRepository.findById(fromClientAccountEntity.getCurrencyId()).get();
                CurrencyEntity toCurrencyEntity = currencyRepository.findById(toClientAccountEntity.getCurrencyId()).get();
                throw new InternalPreconditionFailedException("Can't exchange currency from " + fromCurrencyEntity.getShortName() +
                                                                  " to " + toCurrencyEntity.getShortName());
            }
            BigDecimal rate = new BigDecimal(currencyExchangeEntityOptional.get().getRate());
            rate.setScale(0, RoundingMode.HALF_UP);
            BigDecimal toAddAmount = new BigDecimal(amount);
            toAddAmount.setScale(0, RoundingMode.HALF_UP);
            toAddAmount = toAddAmount.multiply(rate);
            log.info("exchange(): toAddAmount={}, toAddAmount.intValue={}", toAddAmount, toAddAmount.intValue());

            fromClientAccountEntity.setAmount(fromClientAccountEntity.getAmount() - amount);
            clientAccountRepository.save(fromClientAccountEntity);
            toClientAccountEntity.setAmount(toClientAccountEntity.getAmount() + toAddAmount.intValue());
            clientAccountRepository.save(toClientAccountEntity);
            log.info("exchange(): Success");
            return true;
        } catch (InternalPreconditionFailedException e) {
            throw e;
        } catch (Exception e) {
            log.error("can't debit", e);
            throw new InternalRollBackableException();
        }
    }
}
