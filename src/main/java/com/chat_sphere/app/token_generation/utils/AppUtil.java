package com.chat_sphere.app.token_generation.utils;

import com.chat_sphere.app.dtos.Reply;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

@Component
public class AppUtil {

    public Reply getReply(String token){
        if(token == null || token.isBlank()){
            return new Reply();
        }else{
            token = token.substring(7);
            Claims claims = JWTUtils.extractClaims(token);
            if(claims == null) return new Reply();
            Reply reply = new Reply();
            reply.setSession(claims);
            return reply;
        }
    }
}
