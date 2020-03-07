package com.oracle.proof.service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

import com.oracle.proof.model.ScriptRequest;
import com.oracle.proof.model.ScriptResponse;

@Service
public class PythonService {

    private static HashMap<String, PythonInterpreter> paythonListInterpreter = new HashMap<String, PythonInterpreter>();



    public ScriptResponse execute(ScriptRequest interpreterCode, PythonInterpreter interpreter) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        try {
            PythonInterpreter pythonInterpreter = paythonListInterpreter.get(interpreterCode.getSessionId());
            if (pythonInterpreter == null) {
                pythonInterpreter = new PythonInterpreter();
            }
            interpreter.setOut(out);
            interpreter.setErr(err);
            interpreter.exec(interpreterCode.getCode());
            ScriptResponse scriptResponse = new ScriptResponse(out.toString(), "");
            paythonListInterpreter.put(interpreterCode.getSessionId(), interpreter);
            return scriptResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ScriptResponse("", "Error");
    }

}