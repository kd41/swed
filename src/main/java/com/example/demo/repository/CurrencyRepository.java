package com.example.demo.repository;

import com.example.demo.model.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Integer> {

    Optional<CurrencyEntity> findFirstByShortName(String shortName);
}
