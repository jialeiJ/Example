package com.vienna.jaray.repository;

import com.vienna.jaray.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    public List<ChatMessage> findByTypeAndFromInOrToInOrderBySendTimeDesc(String type, List<String> from2ToList, List<String> to2FromList);
}
