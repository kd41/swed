package com.example.demo.model.entity;

import com.example.demo.model.enums.OperationTaskStatusEnum;

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
@Table(name = "operation_task")
@Getter
@Setter
@NoArgsConstructor
public class OperationTaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    @Enumerated(EnumType.STRING)
    private OperationTaskStatusEnum status;
    private String description;
    private String nodeName;
}
