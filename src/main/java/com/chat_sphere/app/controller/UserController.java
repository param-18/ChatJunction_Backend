package com.chat_sphere.app.controller;

import com.chat_sphere.app.constant.MsgCodes;
import com.chat_sphere.app.dtos.Reply;
import com.chat_sphere.app.dtos.UserDTO;
import com.chat_sphere.app.service.UserService;
import com.chat_sphere.app.token_generation.utils.AppUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppUtil appUtil;

    @PostMapping("/sendOTP")
    public Reply sendOTP(HttpServletRequest request, @RequestBody UserDTO userDTO){
        Reply reply = appUtil.getReply(request.getHeader("Authorization"));
        userService.sendOTP(reply,userDTO);
        return reply;
    }

    @PostMapping("/verifyOTP")
    public Reply verifyOTP(HttpServletRequest request, @RequestBody UserDTO userDTO){
        Reply reply = appUtil.getReply(request.getHeader("Authorization"));
        userService.verifyOTP(reply,userDTO);
        return reply;
    }

    @PostMapping("/signin")
    public Reply signin(HttpServletRequest request,@RequestBody UserDTO userDTO) {
        Reply reply = appUtil.getReply(request.getHeader("Authorization"));
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(userDTO.getEmail());
            if (userRecord != null) {
                // User exists, now check the password
              /*  boolean isPasswordCorrect = FirebaseAuth.getInstance().getUserByEmail(userDTO.getEmail());
                if (isPasswordCorrect) {
                    // Successful sign-in
                    reply.setInfo(MsgCodes.USER_SIGNIN_SUCCESS);
                    // You can add user details to the reply here if needed
                } else {
                    reply.setError(MsgCodes.INVALID_PASSWORD);
                }*/
            } else {
                reply.setError(MsgCodes.USER_NOT_FOUND);
            }
        } catch (FirebaseAuthException e) {
            reply.setError(MsgCodes.SIGNIN_FAILED);
        }
        return reply;
    }
}
