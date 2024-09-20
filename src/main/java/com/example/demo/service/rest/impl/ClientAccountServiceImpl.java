package com.example.demo.service.rest.impl;

import com.example.demo.exception.InternalPreconditionFailedException;
import com.example.demo.exception.InternalRollBackableException;
import com.example.demo.model.dto.ClientAccountDTO;
import com.example.demo.model.dto.CurrencyAmountDTO;
import com.example.demo.model.dto.DebitDTO;
import com.example.demo.model.entity.ClientAccountEntity;
import com.example.demo.repository.ClientAccountRepository;
import com.example.demo.service.internal.OperationService;
import com.example.demo.service.rest.ClientAccountService;
import com.example.demo.service.rest.RequestValidatorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientAccountServiceImpl implements ClientAccountService {

    private final ClientAccountRepository clientAccountRepository;

    private final RequestValidatorService requestValidatorService;

    private final ModelMapper modelMapper;

    private final OperationService operationService;

    @Override
    public List<ClientAccountDTO> getAllClientAccountDTOs() {
        log.info("getAllClientAccountDTOs");
        List<ClientAccountEntity> clientAccountEntities = clientAccountRepository.findAll();
        return clientAccountEntities.stream()
                                    .map(e -> modelMapper.map(e, ClientAccountDTO.class))
                                    .collect(Collectors.toList());
    }

    @Override
    public CurrencyAmountDTO getCurrencyAmountDTO(int clientId, String currencyShortName) {
        log.info("getCurrencyAmountDTO: clientId={}, currencyShortName={}", clientId, currencyShortName);
        ClientAccountEntity clientAccountEntity = requestValidatorService.validateAndGetClientAccountEntity(clientId, currencyShortName);
        CurrencyAmountDTO currencyAmountDTO = CurrencyAmountDTO.builder()
                                                               .amount(clientAccountEntity.getAmount())
                                                               .currency(currencyShortName)
                                                               .build();
        log.info("getCurrencyAmountDTO: result={}", currencyAmountDTO);
        return currencyAmountDTO;
    }

    @Override
    public CurrencyAmountDTO addCurrencyAmountDTO(int clientId, DebitDTO debitDTO) throws InternalRollBackableException {
        log.info("addCurrencyAmountDTO: clientId={}, debitDTO={}", clientId, debitDTO);
        ClientAccountEntity clientAccountEntity = requestValidatorService.validateAndGetClientAccountEntity(clientId, debitDTO.getCurrency());
        log.info("debit START");
        operationService.add(clientAccountEntity.getId(), debitDTO.getAmount());
        log.info("debit END");
        clientAccountEntity = clientAccountRepository.findById(clientAccountEntity.getId()).get();
        CurrencyAmountDTO currencyAmountDTO = CurrencyAmountDTO.builder()
                                                               .amount(clientAccountEntity.getAmount())
                                                               .currency(debitDTO.getCurrency())
                                                               .build();
        log.info("addCurrencyAmountDTO: result={}", currencyAmountDTO);
        return currencyAmountDTO;
    }

    @Override
    public CurrencyAmountDTO debitCurrencyAmountDTO(int clientId, DebitDTO debitDTO) throws InternalRollBackableException, InternalPreconditionFailedException {
        log.info("debitCurrencyAmountDTO: clientId={}, debitDTO={}", clientId, debitDTO);
        ClientAccountEntity clientAccountEntity = requestValidatorService.validateAndGetClientAccountEntity(clientId, debitDTO.getCurrency());
        log.info("debit START");
        operationService.debit(clientAccountEntity.getId(), debitDTO.getAmount());
        log.info("debit END");
        clientAccountEntity = clientAccountRepository.findById(clientAccountEntity.getId()).get();
        CurrencyAmountDTO currencyAmountDTO = CurrencyAmountDTO.builder()
                                                               .amount(clientAccountEntity.getAmount())
                                                               .currency(debitDTO.getCurrency())
                                                               .build();
        log.info("debitCurrencyAmountDTO: result={}", currencyAmountDTO);
        return currencyAmountDTO;
    }
}
