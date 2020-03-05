package com.oracle.proof;

import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import com.oracle.proof.facade.AbstractLangInterpreterFactory;
import com.oracle.proof.model.ScriptRequest;

import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.oracle.proof.model.Response;
import com.oracle.proof.service.PythonService;

@Component
@Qualifier("python")
public class PythonInterpreterFactory extends AbstractLangInterpreterFactory {
    static ConcurrentHashMap<String, PythonInterpreter> pythonList = new ConcurrentHashMap<String, PythonInterpreter>();
    @Autowired
    private PythonService pythonService;


    public Response performResponse(ScriptRequest scriptRequest) {
        try {
            PythonInterpreter interpreter = pythonList.get(scriptRequest.getSessionId());
            if (interpreter == null) {
                interpreter = new PythonInterpreter();
            }
            pythonList.put(scriptRequest.getSessionId(), interpreter);
            return pythonService.execute(scriptRequest, interpreter);
        } catch (Exception e) {
            pythonList.remove(scriptRequest.getSessionId());
            return new Response("", "there is an error");
        }
    }
}
