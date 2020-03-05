package com.oracle.proof.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oracle.proof.facade.AbstractLangInterpreterFactory;
import com.oracle.proof.model.Response;
import com.oracle.proof.model.ScriptRequest;

@ComponentScan(basePackages = "com.oracle.proof")
@RestController
public class InterpretorController {
    private static final String REQUEST_PATTERN = "%(\\w+)\\s+(.*)";
    private static final Pattern pattern = Pattern.compile(REQUEST_PATTERN);
    @Autowired
    ApplicationContext applicationContext;

    @PostMapping("/execute")
    public ResponseEntity<Response> execute(@RequestBody ScriptRequest scriptRequest, HttpSession httpSession) throws JsonProcessingException {
        String sessionId = scriptRequest.getSessionId() != null ? scriptRequest.getSessionId() : httpSession.getId();
        getInterpreterRequest(scriptRequest);
        AbstractLangInterpreterFactory abstractLangInterpreterFactory = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getAutowireCapableBeanFactory(), AbstractLangInterpreterFactory.class, scriptRequest.getLanguage());
        Response response= abstractLangInterpreterFactory.performResponse(scriptRequest);
        return ResponseEntity.ok(response);
    }

    private void getInterpreterRequest(ScriptRequest scriptRequest) {
        Matcher matcher = pattern.matcher(scriptRequest.getCode());
        if (matcher.matches()) {
            String language = matcher.group(1);
            String code = matcher.group(2);
            scriptRequest.setCode(code);
            scriptRequest.setLanguage(language);
        }
    }
}
