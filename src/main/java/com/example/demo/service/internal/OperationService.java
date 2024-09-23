package com.example.demo.service.internal;

import com.example.demo.exception.InternalPreconditionFailedException;

public interface OperationService {

    boolean add(int clientAccountId, int amount);

    boolean debit(int clientAccountId, int amount) throws InternalPreconditionFailedException;

    boolean exchange(int clientAccountFromId, int clientAccountToId, int amount) throws InternalPreconditionFailedException;
}
