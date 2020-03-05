package com.oracle.proof.facade;

import com.oracle.proof.model.ScriptRequest;
import com.oracle.proof.model.Response;

public abstract class AbstractLangInterpreterFactory {
    abstract public Response performResponse(ScriptRequest script);
}
