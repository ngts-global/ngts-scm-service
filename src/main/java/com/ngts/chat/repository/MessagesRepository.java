package com.ngts.chat.repository;

import com.ngts.chat.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MessagesRepository extends JpaRepository<Messages, Integer> {

    @Query(nativeQuery = true, value = "SELECT  * FROM messages  a WHERE a.to_id <= :fromId OR a.from_id <= :fromId ORDER BY a.created_at DESC limit 50")
    List<Messages> findByTopMessages(String fromId);
}
