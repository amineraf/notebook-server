package com.oracle.proof.service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import org.python.util.PythonInterpreter;

import com.oracle.proof.model.InterpreterRequest;
import com.oracle.proof.model.RequestCode;
import com.oracle.proof.model.Response;


public class PythonService {
    HashMap<String, PythonInterpreter> paythonListInterpreter = new HashMap<String, PythonInterpreter>();

    public Response execute(InterpreterRequest interpreterCode, PythonInterpreter interpreter) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        try {
            PythonInterpreter pythonInterpreter=paythonListInterpreter.get(interpreterCode.getSessionId());
            if(pythonInterpreter==null)pythonInterpreter=new PythonInterpreter();
            interpreter.setOut(out);
            interpreter.setErr(err);
            interpreter.exec(interpreterCode.getCode());
            Response response = new Response(out.toString(), "");
            paythonListInterpreter.put(interpreterCode.getSessionId(),interpreter);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response("", "");
    }
}
