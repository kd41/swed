package com.example.demo.repository;

import com.example.demo.model.entity.CurrencyExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchangeEntity, Integer> {

    Optional<CurrencyExchangeEntity> findFirstByFromCurrencyIdAndToCurrencyId(int fromCurrencyId, int toCurrencyId);
}
