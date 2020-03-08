package com.oracle.training;

import static java.time.Duration.ofMillis;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.training.controller.InterpreterController;
import com.oracle.training.model.ScriptRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class InterpretorControllerTest {
    private static int BAD_REQUEST = 400;
    @Autowired
    private InterpreterController controller;
    @Autowired
    private MockMvc mvc;
    private String URI = "http://localhost:8081/execute";
    private String VALID_SCRIPT = "%python print 1+1";
    private String INVALID_SCRIPT = "print 1+1";
    private String INVALID_LANG = "%java print 1+1";
    private Long TIMEOUT = 5000L;

    private static String mapToJson(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void whenValidInput_thenReturns200() throws Exception {
        ScriptRequest request = new ScriptRequest();
        request.setCode(VALID_SCRIPT);
        mvc.perform(post(URI).content(mapToJson(request))
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void whenScriptError_thenReturns400() throws Exception {
        ScriptRequest request = new ScriptRequest();
        request.setCode(INVALID_SCRIPT);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(BAD_REQUEST, status);
    }

    @Test
    public void whennotSupportedLang_thenReturns400() throws Exception {
        ScriptRequest request = new ScriptRequest();
        request.setCode(INVALID_LANG);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(BAD_REQUEST, status);
    }

     @Test
    public void timeout_not_exceeded() {
        ScriptRequest request = new ScriptRequest();
        request.setCode(VALID_SCRIPT);
        assertTimeout(ofMillis(TIMEOUT), () -> {
            mvc.perform(post(URI).content(mapToJson(request))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        });
    }
}
