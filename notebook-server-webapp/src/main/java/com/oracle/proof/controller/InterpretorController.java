package com.oracle.proof.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oracle.proof.interpreter.AbstractLangInterpreter;
import com.oracle.proof.model.ScriptRequest;
import com.oracle.proof.model.ScriptResponse;
import com.oracle.proof.validator.CorrectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ComponentScan(basePackages = "com.oracle.proof")
@Validated
@RestController
public class InterpretorController {
    private static final String REQUEST_PATTERN = "%(\\w+)\\s+(.*)";
    private static final Pattern pattern = Pattern.compile(REQUEST_PATTERN);
    @Autowired
    ApplicationContext applicationContext;

    @PostMapping("/execute")
    public Callable<ScriptResponse> execute(@CorrectRequest @RequestBody ScriptRequest scriptRequest, HttpSession httpSession) throws JsonProcessingException {
        return new Callable<ScriptResponse>() {
            @Override
            public ScriptResponse call() {
                scriptRequest.setSessionId(scriptRequest.getSessionId() != null ? scriptRequest.getSessionId() : httpSession.getId());
                getInterpreterRequest(scriptRequest);
                AbstractLangInterpreter abstractLangInterpreter = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getAutowireCapableBeanFactory(), AbstractLangInterpreter.class, scriptRequest.getLanguage());
                ScriptResponse scriptResponse = abstractLangInterpreter.performResponse(scriptRequest);
                return scriptResponse;
            }
        };
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
