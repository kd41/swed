package com.example.demo.repository;

import com.example.demo.model.entity.ClientAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

import java.util.List;

public interface ClientAccountRepository extends JpaRepository<ClientAccountEntity, Integer> {

    List<ClientAccountEntity> findAllByClientId(int clientId);

    @Query(nativeQuery = true,
           value = "select * from client_account " +
               "where client_id = ?1 " +
               "and currency_id = (select id from currency where short_name = ?2) " +
               "and status = 'OPEN'")
    ClientAccountEntity getClientAccountOpen(int clientId, String currencyShortName);

    @Query(nativeQuery = true,
           value = "select * from client_account " +
               "where client_id = ?1 " +
               "and currency_id = (select id from currency where short_name = ?2)")
    ClientAccountEntity getClientAccount(int clientId, String currencyShortName);

    @Query("select cae from ClientAccountEntity cae where cae.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    ClientAccountEntity lockById(@Param("id") int id);
}
