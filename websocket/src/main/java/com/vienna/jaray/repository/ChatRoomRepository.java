package com.vienna.jaray.repository;

import com.vienna.jaray.model.ChatMessage;
import com.vienna.jaray.model.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

}
