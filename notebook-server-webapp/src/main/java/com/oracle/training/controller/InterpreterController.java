package com.oracle.training.controller;

import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.training.exception.InterpreterException;
import com.oracle.training.interpreter.AbstractLangInterpreter;
import com.oracle.training.model.LanguageEnum;
import com.oracle.training.model.ScriptRequest;
import com.oracle.training.model.ScriptResponse;
import com.oracle.training.validator.ValidatePayload;

@Validated
@RestController
public class InterpreterController {
    private static final String REQUEST_PATTERN = "%(\\w+)\\s+(.*)";
    private static final Pattern pattern = Pattern.compile(REQUEST_PATTERN);
    private static String NOT_SUPPORTED_LANG = "Language not supported yet!";
    @Autowired
    ApplicationContext applicationContext;

    @PostMapping("/execute")
    public Callable<ScriptResponse> executeC(@ValidatePayload @RequestBody ScriptRequest scriptRequest, HttpSession httpSession) throws InterpreterException {
        Callable<ScriptResponse> callableObj = () -> {
            scriptRequest.setSessionId(scriptRequest.getSessionId() != null ? scriptRequest.getSessionId() : httpSession.getId());
            getInterpreterRequest(scriptRequest);
            if (!supportedLang(scriptRequest.getLanguage())) {
                throw new InterpreterException(NOT_SUPPORTED_LANG);
            }
            AbstractLangInterpreter abstractLangInterpreter = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getAutowireCapableBeanFactory(), AbstractLangInterpreter.class, scriptRequest.getLanguage());
            ScriptResponse scriptResponse = abstractLangInterpreter.execute(scriptRequest);
            return scriptResponse;
        };
        return callableObj;
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

    private boolean supportedLang(String language) {
        for (LanguageEnum lang : LanguageEnum.values()) {
            if (lang.getLangName().equals(language)) {
                return true;
            }
        }
        return false;
    }
}
