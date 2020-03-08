package com.oracle.training;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.python.util.PythonInterpreter;

import com.oracle.training.exception.InterpreterException;
import com.oracle.training.model.ScriptRequest;
import com.oracle.training.model.ScriptResponse;
import com.oracle.training.service.PythonInterpreterService;

@RunWith(MockitoJUnitRunner.class)
public class PythonInterpreterServiceTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    private PythonInterpreterService pythonInterpreterService;
    private String INVALID_SCRIPT = "print a";
    private String VALID_SCRIPT_WITH_VAR = "a=1";
    private String VALID_SCRIPT_OPERATION = "print 1+1";
    private ScriptRequest scriptRequest;
    private PythonInterpreter interpreter;

    @Before
    public void setUp() {
        pythonInterpreterService = new PythonInterpreterService();
        scriptRequest = new ScriptRequest();
        interpreter = new PythonInterpreter();
    }

    @Test
    public void should_execute_script() throws InterpreterException {
        scriptRequest.setCode(VALID_SCRIPT_WITH_VAR);
        assertThat(isInvalidResult(pythonInterpreterService.execute(scriptRequest, interpreter)), Is.is(false));
        scriptRequest.setCode(VALID_SCRIPT_OPERATION);
        assertThat(isInvalidResult(pythonInterpreterService.execute(scriptRequest, interpreter)), Is.is(false));
    }

    private boolean isInvalidResult(ScriptResponse scriptResponse) {
        return scriptResponse.getError() != null && !scriptResponse.getError().isEmpty();
    }

    @Test(expected = InterpreterException.class)
    public void exp_should_throw_exception_when_script_invalid() throws InterpreterException {
        scriptRequest.setCode(INVALID_SCRIPT);
        pythonInterpreterService.execute(scriptRequest, interpreter);
    }
}
