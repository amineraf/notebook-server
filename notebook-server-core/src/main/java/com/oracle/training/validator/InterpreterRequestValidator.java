package com.oracle.training.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.oracle.training.model.ScriptRequest;

public class InterpreterRequestValidator implements ConstraintValidator<ValidatePayload, ScriptRequest> {

    private static final String CODE_MISSING = "Request code is required";
    private static final String REQUEST_PATTERN = "%(\\w+)\\s+(.*)";

    @Override
    public boolean isValid(ScriptRequest request, ConstraintValidatorContext context) {
        if (request == null || request.getCode() == null || request.getCode().isEmpty()) {
            context.buildConstraintViolationWithTemplate(CODE_MISSING)
                .addPropertyNode("code")
                .addConstraintViolation();
            return false;
        }

        if (!patternValidator(request.getCode())) {
            return false;
        }
        return true;
    }

    public boolean patternValidator(String code) {
        if (code == null) {
            return false;
        }
        return code.matches(REQUEST_PATTERN);
    }
}
