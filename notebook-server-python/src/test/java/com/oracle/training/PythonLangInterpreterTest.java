package com.oracle.training;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.python.util.PythonInterpreter;

import com.oracle.training.exception.InterpreterException;
import com.oracle.training.model.ScriptRequest;
import com.oracle.training.model.ScriptResponse;

public class PythonLangInterpreterTest {

    private static final String SESSION_ID = "9632665485475544";
    private PythonLangInterpreter pythonLangInterpreter;
    private String INVALID_SCRIPT = "print a";
    private String VALID_SCRIPT_OPERATION = "print 1+1";
    private ScriptRequest scriptRequest;
    private PythonInterpreter interpreter;

    @Before
    public void setUp() {
        pythonLangInterpreter = new PythonLangInterpreter();
        scriptRequest = new ScriptRequest();
        interpreter = new PythonInterpreter();
    }

    @Test
    public void sould_excute_script() throws InterpreterException {
        scriptRequest.setCode(VALID_SCRIPT_OPERATION);
        scriptRequest.setSessionId(SESSION_ID);
        assertThat(isInvalidResult(pythonLangInterpreter.execute(scriptRequest)), Is.is(false));
        assertNotNull(PythonLangInterpreter.pythonList.get(scriptRequest.getSessionId()));
        scriptRequest.setCode(INVALID_SCRIPT);
        assertThat(isInvalidResult(pythonLangInterpreter.execute(scriptRequest)), Is.is(true));
    }

    private boolean isInvalidResult(ScriptResponse scriptResponse) {
        return scriptResponse.getError() != null && !scriptResponse.getError().isEmpty();
    }
}
