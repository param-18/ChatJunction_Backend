package com.chat_sphere.app.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ChatDTO extends RequestResponseDTO{

    private String id;
    private String senderId;
    private String receiverId;
    private String message;
    private Date timeStamp;
    private String profilePictureUrl;
}
