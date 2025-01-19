package com.chat_sphere.app.service;

import com.chat_sphere.app.constant.Constant;
import com.chat_sphere.app.constant.MsgCodes;
import com.chat_sphere.app.dtos.Reply;
import com.chat_sphere.app.dtos.UserDTO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private OtpService otpService;

    public UserRecord addUser(UserDTO userDTO) {
        UserRecord userRecord = null;
        try {
            userRecord = FirebaseAuth.getInstance()
                    .createUser(new UserRecord.CreateRequest()
                            .setEmail(userDTO.getEmail())
                            .setPhoneNumber(userDTO.getPhoneNumber())
                            .setDisplayName(userDTO.getName())
                            .setPassword(userDTO.getPassword()));
            return userRecord;
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }finally {
            return userRecord;
        }
    }

    public UserRecord getUserByPhoneNumber(String phoneNumber){
        UserRecord userRecord = null;
        try {
            userRecord = FirebaseAuth.getInstance().getUserByPhoneNumber(phoneNumber);
            return userRecord;
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }finally {
            return null;
        }
    }

    public void sendOTP(Reply reply, UserDTO userDTO) {
        if(userDTO.getPhoneNumber() == null || userDTO.getPhoneNumber().trim().isEmpty()){
            reply.setError(MsgCodes.SPECIFY_PHONENO);
            return;
        }
        otpService.sendOtp(userDTO.getPhoneNumber());
        reply.setInfo(MsgCodes.OTP_SENT);
    }

    public void verifyOTP(Reply reply, UserDTO userDTO) {
        if(userDTO.getPhoneNumber() == null || userDTO.getPhoneNumber().trim().isEmpty()){
            reply.setError(MsgCodes.SPECIFY_PHONENO);
            return;
        }
        if(userDTO.getOtp() == null || userDTO.getOtp().trim().isEmpty()){
            reply.setError(MsgCodes.SPECIFY_OTP);
            return;
        }
        boolean result = otpService.verifyOtp(userDTO.getPhoneNumber(),userDTO.getOtp());
        if(result){
            UserRecord userRecord = getUserByPhoneNumber(userDTO.getPhoneNumber().trim());
            userDTO.setAlreadyRegistered(userRecord != null);
            userDTO.setName(userRecord != null ? userRecord.getDisplayName() : null);
            reply.setAttribute(Constant.LOGGED_IN_USER_PHONE_NUMBER,userDTO.getPhoneNumber());
            reply.setData(userDTO);
            reply.setInfo(MsgCodes.USER_VERIFIED);
        }else{
            reply.setError(MsgCodes.USER_NOT_VERIFIED);
        }
    }
}
