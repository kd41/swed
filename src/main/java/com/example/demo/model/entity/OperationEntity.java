package com.example.demo.model.entity;

import com.example.demo.model.enums.OperationStatusEnum;
import com.example.demo.model.enums.OperationTypeEnum;

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
@Table(name = "operation")
@Getter
@Setter
@NoArgsConstructor
public class OperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private OperationTypeEnum type;
    private int amount;
    private String uuid;
    private int clientAccountFromId;
    private int clientAccountToId;
    private Integer parent_operation_id;
    private int operationOrder;
    @Enumerated(EnumType.STRING)
    private OperationStatusEnum status;
    private Boolean success;
    private int failoverCount;
    private LocalDateTime creationDate;
}
