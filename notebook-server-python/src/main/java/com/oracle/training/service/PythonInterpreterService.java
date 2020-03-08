package com.oracle.training.service;

import java.io.ByteArrayOutputStream;

import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

import com.oracle.training.exception.InterpreterException;
import com.oracle.training.model.ScriptRequest;
import com.oracle.training.model.ScriptResponse;

@Service
public class PythonInterpreterService {

    public ScriptResponse execute(ScriptRequest interpreterCode, PythonInterpreter interpreter) throws InterpreterException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        try {
            interpreter.setOut(out);
            interpreter.setErr(err);
            interpreter.exec(interpreterCode.getCode());
            ScriptResponse scriptResponse = new ScriptResponse(out.toString(), "");
            return scriptResponse;
        } catch (Exception e) {
            throw new InterpreterException();
        }
    }
}