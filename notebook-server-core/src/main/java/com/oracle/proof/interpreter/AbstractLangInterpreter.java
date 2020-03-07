package com.oracle.proof.interpreter;

import com.oracle.proof.model.ScriptRequest;
import com.oracle.proof.model.ScriptResponse;

public abstract class AbstractLangInterpreter {
    abstract public ScriptResponse performResponse(ScriptRequest script);
}
