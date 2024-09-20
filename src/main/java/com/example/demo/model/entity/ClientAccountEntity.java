package com.example.demo.model.entity;

import com.example.demo.model.enums.AccountStatusEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "client_account")
@Getter
@Setter
@NoArgsConstructor
public class ClientAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int clientId;
    private int currencyId;
    private int amount;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    @Enumerated(EnumType.STRING)
    private AccountStatusEnum status;
}
