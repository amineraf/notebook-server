package com.oracle.training;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.oracle.training.model.ScriptRequest;
import com.oracle.training.validator.InterpreterRequestValidator;

@RunWith(MockitoJUnitRunner.class)
public class InterpreterRequestValidatorTest {

    private InterpreterRequestValidator interpreterRequestValidator;
    private ScriptRequest scriptRequest;
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;
    private static String VALID_SCRIPT_WITHOUT_PERCENT = "python print 1+1";
    private static String INVALID_SCRIPT = "python 22";
    private static String VALID_SCRIPT = "%python print 1+1";

    @Mock
    private ConstraintValidatorContext context;

    @Before
    public void setUp() {
        scriptRequest = new ScriptRequest();
        context = mock(ConstraintValidatorContext.class);
        interpreterRequestValidator = mock(InterpreterRequestValidator.class);
        builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);

        when(interpreterRequestValidator.isValid(any(), any())).thenCallRealMethod();
        when(interpreterRequestValidator.patternValidator(anyString())).thenCallRealMethod();
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        when(builder.addPropertyNode(anyString())).thenReturn(mock(NodeBuilderCustomizableContext.class));
    }

    @Test
    public void should_test_request_validator() {
        assertThat(interpreterRequestValidator.isValid(scriptRequest, context), is(false));
        scriptRequest.setCode(VALID_SCRIPT_WITHOUT_PERCENT);
        assertThat(interpreterRequestValidator.isValid(scriptRequest, context), is(false));
        scriptRequest.setCode(VALID_SCRIPT);
        assertThat(interpreterRequestValidator.isValid(scriptRequest, context), is(true));
    }

    @Test
    public void should_validate_loaded_request() {
        assertFalse(interpreterRequestValidator.patternValidator(VALID_SCRIPT_WITHOUT_PERCENT));
        assertFalse(interpreterRequestValidator.patternValidator(INVALID_SCRIPT));
        assertFalse(interpreterRequestValidator.patternValidator(null));
        assertTrue(interpreterRequestValidator.patternValidator(VALID_SCRIPT));
    }
}
