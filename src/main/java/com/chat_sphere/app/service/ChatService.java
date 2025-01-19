package com.chat_sphere.app.service;

import com.chat_sphere.app.dtos.ChatDTO;
import com.chat_sphere.app.dtos.Reply;
import com.chat_sphere.app.dtos.UserDTO;
import com.chat_sphere.app.utils.Utils;
import com.google.firebase.database.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ChatService {

    public void getChats(Reply reply, UserDTO userDTO) {
        DatabaseReference chatsPathReference = FirebaseDatabase.getInstance("https://chat-sphere-c1bf6-default-rtdb.firebaseio.com/").getReference("chats");
        //DatabaseReference userChatsPathReference = chatsPathReference.child(Utils.getUserPhoneNumber(reply));
        CompletableFuture<DataSnapshot> chatsSnapshot = new CompletableFuture<>();
        chatsPathReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                chatsSnapshot.complete(snapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                chatsSnapshot.completeExceptionally(error.toException());
            }
        });
        try {
            DataSnapshot snapshot = chatsSnapshot.get();
            if(snapshot.exists() && snapshot.hasChildren()){
                List<ChatDTO> chatDTOS = new ArrayList<>();
                for(DataSnapshot chatNode : snapshot.getChildren()){
                    if(chatNode.child("senderId").getValue(String.class).equalsIgnoreCase(userDTO.getPhoneNumber() == null || userDTO.getPhoneNumber().isBlank() ? Utils.getUserPhoneNumber(reply) : userDTO.getPhoneNumber())) {
                        ChatDTO chatDTO = new ChatDTO();
                        chatDTO.setId(chatNode.getKey());
                        chatDTO.setSenderId(chatNode.child("senderId").getValue(String.class));
                        chatDTO.setReceiverId(chatNode.child("receiverId").getValue(String.class));
                        chatDTO.setMessage(chatNode.child("message").getValue(String.class));
                        chatDTO.setTimeStamp(Utils.fromISOString(chatNode.child("timeStamp").getValue(String.class)));
                        chatDTO.setProfilePictureUrl(chatNode.child("profilePictureUrl").getValue(String.class));
                        chatDTOS.add(chatDTO);
                    }
                }
                if(chatDTOS.isEmpty()){
                    reply.setError("No chats found");
                }else {
                    reply.setData(chatDTOS);
                }
            }else{
                reply.setError("No chats found");
                return;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
