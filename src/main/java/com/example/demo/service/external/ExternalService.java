package com.example.demo.service.external;

import com.example.demo.exception.InternalRetryException;

public interface ExternalService {

    void callExternal() throws InternalRetryException;
}
