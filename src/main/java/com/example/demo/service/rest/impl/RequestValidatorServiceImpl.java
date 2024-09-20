package com.example.demo.service.rest.impl;

import com.example.demo.exception.MainBadRequestException;
import com.example.demo.exception.MainNotFoundException;
import com.example.demo.model.entity.ClientAccountEntity;
import com.example.demo.repository.ClientAccountRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.CurrencyRepository;
import com.example.demo.service.rest.RequestValidatorService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestValidatorServiceImpl implements RequestValidatorService {

    private final ClientRepository clientRepository;
    private final CurrencyRepository currencyRepository;
    private final ClientAccountRepository clientAccountRepository;

    @Override
    public ClientAccountEntity validateAndGetClientAccountEntity(int clientId, String currencyShortName) {
        if (!clientRepository.existsById(clientId)) {
            throw new MainNotFoundException("Client with id=" + clientId + " not exists!");
        }
        if (!currencyRepository.findFirstByShortName(currencyShortName).isPresent()) {
            throw new MainNotFoundException("Currency with shortName=" + currencyShortName + " not exists!");
        }
        ClientAccountEntity clientAccountEntity = clientAccountRepository.getClientAccountOpen(clientId, currencyShortName);
        if (clientAccountEntity != null) {
            return clientAccountEntity;
        } else {
            clientAccountEntity = clientAccountRepository.getClientAccount(clientId, currencyShortName);
            if (clientAccountEntity != null) {
                throw new MainBadRequestException("Client with id=" + clientId + " and currency with shortName=" + currencyShortName + " is not open!");
            }
            throw new MainNotFoundException("Client with id=" + clientId + " and currency with shortName=" + currencyShortName + " not exists!");
        }
    }
}
