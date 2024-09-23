package com.example.demo.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class CurrencyExchangeDTO {

    private int amount;
    private String fromCurrency;
    private String toCurrency;
}
