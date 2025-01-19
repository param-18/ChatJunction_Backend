package com.chat_sphere.app.controller;

import com.chat_sphere.app.dtos.Reply;
import com.chat_sphere.app.token_generation.utils.AppUtil;
import com.chat_sphere.app.token_generation.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/initial")
public class InitialController {

    @Autowired
    private AppUtil appUtil;

    @GetMapping("/generateToken")
    public Reply generateToken(HttpServletRequest request) {
        Reply reply = appUtil.getReply(request.getHeader("Authorization"));
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains("Android") || userAgent.contains("iOS")) {
            reply.setToken(JWTUtils.generateToken(reply,userAgent));
        }else {
            reply.setError("Invalid device");
        }
        return reply;
    }
}
