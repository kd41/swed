package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DebitDTO {

    private int clientId;
    private int amount;
    private String currency;
}
