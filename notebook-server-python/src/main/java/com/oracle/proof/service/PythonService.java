package com.oracle.proof.service;

import com.oracle.proof.model.Response;
import com.oracle.proof.model.ScriptRequest;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class PythonService {

    private static HashMap<String, PythonInterpreter> paythonListInterpreter = new HashMap<String, PythonInterpreter>();
    private Response response = new Response();

    public Response execute(ScriptRequest interpreterCode, PythonInterpreter interpreter) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
       // Future<String> future = executor.submit(new Task());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();

        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    PythonInterpreter pythonInterpreter = paythonListInterpreter.get(interpreterCode.getSessionId());
                    if (pythonInterpreter == null) {
                        pythonInterpreter = new PythonInterpreter();
                    }
                    interpreter.setOut(out);
                    interpreter.setErr(err);

                } catch (Exception e) {
                    e.printStackTrace();
                    response = new Response("", "time out");
                }
            }
        },120000);
        try{
            Thread.sleep(30000);
            interpreter.exec(interpreterCode.getCode());
            response = new Response(out.toString(), "");
            paythonListInterpreter.put(interpreterCode.getSessionId(), interpreter);
        } catch (InterruptedException  e) {
        e.printStackTrace();
        response = new Response("", "time out");
    }
        return response;
    }
}
