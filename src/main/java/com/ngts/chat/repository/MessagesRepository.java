package com.ngts.chat.repository;

import com.ngts.chat.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MessagesRepository extends JpaRepository<Messages, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM messages a WHERE a.to_id = :fromId OR a.from_id = :fromId ORDER BY a.created_at DESC")
    List<Messages> findByFromId(String fromId);
    @Query(nativeQuery = true, value = "SELECT * FROM messages a WHERE (a.to_id = :fromId OR a.from_id = :fromId) AND (a.to_id = :toId OR a.from_id = :toId) ORDER BY a.created_at DESC limit 50")
    List<Messages> findMessagesBetweenUsers(String fromId, String toId);
}
