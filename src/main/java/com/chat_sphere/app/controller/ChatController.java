package com.chat_sphere.app.controller;

import com.chat_sphere.app.dtos.Reply;
import com.chat_sphere.app.dtos.UserDTO;
import com.chat_sphere.app.service.ChatService;
import com.chat_sphere.app.token_generation.utils.AppUtil;
import com.chat_sphere.app.token_generation.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {

    @Autowired
    private AppUtil appUtil;

    @Autowired
    private ChatService chatService;

    @PostMapping
    public Reply getChats(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token, @RequestHeader(value = HttpHeaders.USER_AGENT) String userAgent, @RequestBody UserDTO userDTO){
        Reply reply = appUtil.getReply(token);
        if (userAgent.contains("Android") || userAgent.contains("iOS")) {
            reply.setToken(JWTUtils.generateToken(reply,userAgent));
        }else {
            reply.setError("Invalid device");
        }
        chatService.getChats(reply,userDTO);
        return reply;
    }
}
