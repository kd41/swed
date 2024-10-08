package com.example.demo.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CurrencyAmountDTO {

    private int amount;
    private String currency;
}
