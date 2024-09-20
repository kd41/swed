package com.example.demo.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "currency_exchange")
@Getter
@Setter
@NoArgsConstructor
public class CurrencyExchangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int fromCurrencyId;
    private int toCurrencyId;
    private String rate;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
}
