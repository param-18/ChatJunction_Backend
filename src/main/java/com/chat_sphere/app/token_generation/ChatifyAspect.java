package com.chat_sphere.app.token_generation;

import com.chat_sphere.app.dtos.Reply;
import com.chat_sphere.app.token_generation.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class ChatifyAspect {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public ChatifyAspect(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @AfterReturning(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PatchMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)", returning = "result")
    public void refreshToken(Object result) {
        // Add the token to the response headers
        if(result instanceof Reply){
            String userAgent = request.getHeader("User-Agent");
            String newToken = JWTUtils.generateToken((Reply) result,userAgent);
            ((Reply) result).setToken("Bearer "+newToken);
            response.setHeader("Authorization", "Bearer " + newToken);
            response.setHeader("Token-Expiry", String.valueOf(new Date(System.currentTimeMillis() + 3600 * 1000)));
        }
    }
}
