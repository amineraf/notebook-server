package com.oracle.proof;

import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.oracle.proof.model.AvailableLang;
import com.oracle.proof.model.InterpreterRequest;
import com.oracle.proof.model.RequestCode;

import org.python.util.PythonInterpreter;

import com.oracle.proof.facade.AbstractFactoryResponse;
import com.oracle.proof.model.Response;
import com.oracle.proof.service.PythonService;

public class PythonFactoryResponse extends AbstractFactoryResponse<PythonInterpreter> {
    private static final String REQUEST_PATTERN = "%(\\w+)\\s+(.*)";
    private static final Pattern pattern = Pattern.compile(REQUEST_PATTERN);
    private static String PYTHON_LANG = "python";
    PythonService pythonService = new PythonService();

    public Response performResponse(RequestCode requestCode,ConcurrentHashMap<String, PythonInterpreter> pythonList) {
        PythonInterpreter interpreter = pythonList.get(requestCode.getSessionId());
        InterpreterRequest interpreterRequest = getLanguage(requestCode);
        if(interpreter==null){
            interpreter=new PythonInterpreter();
        }
        if ((PYTHON_LANG).equals(interpreterRequest.getLanguage())) {
            return pythonService.execute(interpreterRequest, interpreter);
        }
        return new Response("", "");
    }

    InterpreterRequest getLanguage(RequestCode requestCode) {
        Matcher matcher = pattern.matcher(requestCode.getCode());
        if (matcher.matches()) {
            String language = matcher.group(1);
            String code = matcher.group(2);

            InterpreterRequest interpreterRequest = new InterpreterRequest();
            interpreterRequest.setCode(code);
            interpreterRequest.setLanguage(language);
            interpreterRequest.setSessionId(requestCode.getSessionId());
            return interpreterRequest;
        }
        return new InterpreterRequest();
    }
}
