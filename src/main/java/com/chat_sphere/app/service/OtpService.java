package com.chat_sphere.app.service;

import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.verification-sid}")
    private String verifySID;

    public void sendOtp(String phoneNumber) {
        Twilio.init(accountSid, authToken);
        Verification verification =
                Verification.creator(verifySID, phoneNumber, "sms")
                        .create();
        System.out.println("OTP sent to " + verification);
    }

    public boolean verifyOtp(String phoneNumber , String otp) {
        Twilio.init(accountSid, authToken);
        VerificationCheck check = VerificationCheck.creator(verifySID,otp)
                .setTo(phoneNumber).create();
        System.out.println(check);
        return check.getStatus().equalsIgnoreCase("approved");
    }
}

