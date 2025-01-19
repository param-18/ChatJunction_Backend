package com.chat_sphere.app.controller;

import com.chat_sphere.app.dtos.Reply;
import com.chat_sphere.app.token_generation.utils.AppUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class WelcomeController {

    @Autowired
    private AppUtil appUtil;

    @GetMapping("/")
    public SseEmitter continuousResponseTest(HttpServletRequest request){
        Reply reply = appUtil.getReply(request.getHeader("Authorization"));
        SseEmitter sseEmitter = new SseEmitter();
        Executors.newSingleThreadExecutor().execute(()-> {
            try {
                for (int i = 0; i < 10; i++) {
                    sseEmitter.send("Event " + i);
                    Thread.sleep(1000); // simulate delay
                }
                sseEmitter.complete();
            } catch (IOException | InterruptedException e) {
                sseEmitter.completeWithError(e);

            }
        });
        return sseEmitter;
    }
}
