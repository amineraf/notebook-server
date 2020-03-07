package com.oracle.training.interpreter;

import com.oracle.training.model.ScriptRequest;
import com.oracle.training.model.ScriptResponse;

public abstract class AbstractLangInterpreter {
    abstract public ScriptResponse execute(ScriptRequest script);
}
