package com.example.demo.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class CurrencyAmountDTO {

    private int amount;
    private String currency;
}
