package com.oracle.proof;

import java.util.concurrent.ConcurrentHashMap;

import com.oracle.proof.interpreter.AbstractLangInterpreter;
import com.oracle.proof.model.ScriptRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.oracle.proof.model.ScriptResponse;
import com.oracle.proof.service.PythonService;

@Component
@Qualifier("python")
public class PythonInterpreter extends AbstractLangInterpreter {
    static ConcurrentHashMap<String, org.python.util.PythonInterpreter> pythonList = new ConcurrentHashMap<String, org.python.util.PythonInterpreter>();
    @Autowired
    private PythonService pythonService;


    public ScriptResponse performResponse(ScriptRequest scriptRequest) {
        try {
            org.python.util.PythonInterpreter interpreter = pythonList.get(scriptRequest.getSessionId());
            if (interpreter == null) {
                interpreter = new org.python.util.PythonInterpreter();
            }
            pythonList.put(scriptRequest.getSessionId(), interpreter);
            return pythonService.execute(scriptRequest, interpreter);
        } catch (Exception e) {
            pythonList.remove(scriptRequest.getSessionId());
            return new ScriptResponse("", "there is an error");
        }
    }
}
