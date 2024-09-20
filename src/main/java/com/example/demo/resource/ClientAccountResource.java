package com.example.demo.resource;

import com.example.demo.exception.InternalPreconditionFailedException;
import com.example.demo.exception.InternalRollBackableException;
import com.example.demo.model.dto.ClientAccountDTO;
import com.example.demo.model.dto.CurrencyAmountDTO;
import com.example.demo.model.dto.DebitDTO;
import com.example.demo.service.rest.ClientAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/client-account/")
@RequiredArgsConstructor
public class ClientAccountResource {

    private final ClientAccountService clientAccountService;

    @GetMapping(value = "get-all")
    public ResponseEntity<List<ClientAccountDTO>> getClients() {
        return ResponseEntity.ok(clientAccountService.getAllClientAccountDTOs());
    }

    @GetMapping(value = "get/{clientId}/{currencyShortName}")
    public ResponseEntity<CurrencyAmountDTO> getCurrencyAmount(
        @PathVariable("clientId") int clientId,
        @PathVariable("currencyShortName") String currencyShortName) {
        return ResponseEntity.ok(clientAccountService.getCurrencyAmountDTO(clientId, currencyShortName));
    }

    @PatchMapping(value = "add/{clientId}")
    public ResponseEntity<CurrencyAmountDTO> add(
        @PathVariable("clientId") int clientId,
        @RequestBody DebitDTO debitDTO) throws InternalRollBackableException {
        return ResponseEntity.ok(clientAccountService.addCurrencyAmountDTO(clientId, debitDTO));
    }

    @PatchMapping(value = "debit/{clientId}")
    public ResponseEntity<CurrencyAmountDTO> debit(
        @PathVariable("clientId") int clientId,
        @RequestBody DebitDTO debitDTO) throws InternalRollBackableException, InternalPreconditionFailedException {
        return ResponseEntity.ok(clientAccountService.debitCurrencyAmountDTO(clientId, debitDTO));
    }
}
