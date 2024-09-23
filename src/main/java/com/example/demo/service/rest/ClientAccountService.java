package com.example.demo.service.rest;

import com.example.demo.exception.InternalPreconditionFailedException;
import com.example.demo.exception.InternalRollBackableException;
import com.example.demo.model.dto.ClientAccountDTO;
import com.example.demo.model.dto.CurrencyAmountDTO;
import com.example.demo.model.dto.CurrencyExchangeDTO;
import com.example.demo.model.dto.DebitDTO;
import com.example.demo.model.dto.MainResponseDTO;

import java.util.List;

public interface ClientAccountService {

    List<ClientAccountDTO> getAllClientAccountDTOs();

    CurrencyAmountDTO getCurrencyAmountDTO(int clientId, String currencyShortName);

    CurrencyAmountDTO addCurrencyAmountDTO(int clientId, DebitDTO debitDTO) throws InternalRollBackableException;

    CurrencyAmountDTO debitCurrencyAmountDTO(int clientId, DebitDTO debitDTO) throws InternalRollBackableException, InternalPreconditionFailedException;

    MainResponseDTO handleCurrencyExchangeDTO(int clientId, CurrencyExchangeDTO currencyExchangeDTO) throws InternalPreconditionFailedException;
}
