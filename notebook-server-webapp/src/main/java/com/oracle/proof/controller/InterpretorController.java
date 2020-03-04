package com.oracle.proof.controller;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.python.util.PythonInterpreter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.proof.PythonFactoryResponse;
import com.oracle.proof.facade.AbstractFactoryResponse;
import com.oracle.proof.model.RequestCode;
import com.oracle.proof.model.Response;

@RestController
public class InterpretorController {
    ConcurrentHashMap<String, PythonInterpreter> pythonList = new ConcurrentHashMap<String, PythonInterpreter>();
    @PostMapping("/execute")
    public ResponseEntity<Response> getObject(@RequestBody RequestCode requestCode, HttpSession httpSession) throws JsonProcessingException {
        String sessionId = requestCode.getSessionId() != null ? requestCode.getSessionId() : httpSession.getId();
        PythonFactoryResponse factoryResponse=new PythonFactoryResponse();
        Response response=factoryResponse.performResponse(requestCode,pythonList);

        return ResponseEntity.ok(response);
    }
}
