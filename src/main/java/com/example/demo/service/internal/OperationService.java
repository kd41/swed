package com.example.demo.service.internal;

import com.example.demo.exception.InternalPreconditionFailedException;
import com.example.demo.exception.InternalRollBackableException;

public interface OperationService {

    boolean add(int clientAccountId, int amount) throws InternalRollBackableException;

    boolean debit(int clientAccountId, int amount) throws InternalRollBackableException, InternalPreconditionFailedException;
}
