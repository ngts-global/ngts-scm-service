package com.ngts.chat.repository;

import com.ngts.chat.entity.ChatUserEntity;
import com.ngts.chat.entity.EOnlineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatUserRepository extends JpaRepository<ChatUserEntity, String> {
    List<ChatUserEntity> findAllByStatus(EOnlineStatus status);
    List<ChatUserEntity> findAllByEmail(String email);
}
