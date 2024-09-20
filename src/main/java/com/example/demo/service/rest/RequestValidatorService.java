package com.example.demo.service.rest;

import com.example.demo.model.entity.ClientAccountEntity;

public interface RequestValidatorService {

    public ClientAccountEntity validateAndGetClientAccountEntity(int clientId, String currencyShortName);
}
