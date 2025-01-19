package com.chat_sphere.app.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO extends RequestResponseDTO {

    private String email;
    private String password;
    private String phoneNumber;
    private String otp;
    private String name;
    private boolean alreadyRegistered;
}
