package com.vienna.jaray.service;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.model.ChatRoom;
import com.vienna.jaray.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public ResponseResult createChatRoom(List<String> userIdList) {

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setUserIdList(userIdList);
        ChatRoom result = chatRoomRepository.save(chatRoom);

        return ResponseResult.success().add("chatRoom", result);
    }
}
