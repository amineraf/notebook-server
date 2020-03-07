package com.oracle.training;

import java.util.concurrent.ConcurrentHashMap;

import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.oracle.training.interpreter.AbstractLangInterpreter;
import com.oracle.training.model.ScriptRequest;
import com.oracle.training.model.ScriptResponse;
import com.oracle.training.service.PythonInterpreterService;

@Component
@Qualifier("python")
public class PythonLangInterpreter extends AbstractLangInterpreter {
    static ConcurrentHashMap<String, org.python.util.PythonInterpreter> pythonList = new ConcurrentHashMap<String, org.python.util.PythonInterpreter>();
    private String PARSING_ERROR = "Interpreting error with Language: ";
    @Autowired
    private PythonInterpreterService pythonInterpreterService;

    public ScriptResponse execute(ScriptRequest scriptRequest) {
        try {
            PythonInterpreter interpreter = pythonList.get(scriptRequest.getSessionId());
            if (interpreter == null) {
                interpreter = new PythonInterpreter();
            }
            pythonList.put(scriptRequest.getSessionId(), interpreter);
            return pythonInterpreterService.execute(scriptRequest, interpreter);
        } catch (Exception e) {
            pythonList.remove(scriptRequest.getSessionId());
            return new ScriptResponse("", PARSING_ERROR +scriptRequest.getLanguage());
        }
    }
}