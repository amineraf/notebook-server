package com.oracle.proof.facade;

import java.util.concurrent.ConcurrentHashMap;

import com.oracle.proof.model.AvailableLang;
import com.oracle.proof.model.RequestCode;
import com.oracle.proof.model.Response;

public abstract class AbstractFactoryResponse<T> {
    abstract public Response performResponse(RequestCode code, ConcurrentHashMap<String, T> interpretor);

}
