package com.ngts.chat.repository;

import com.ngts.auth.entity.Users;
import com.ngts.chat.entity.ChatUserEntity;
import com.ngts.chat.entity.enum1.EChatRegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatUserRepository extends JpaRepository<ChatUserEntity, String> {
    List<ChatUserEntity> findAllByRegistrationStatus(EChatRegistrationStatus status);
    List<ChatUserEntity> findAllByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<ChatUserEntity> findAllByChatId(String chatId);
    Optional<List<ChatUserEntity>> findAllUsersByEmailAndPassword(@Param("email") String email, @Param("password") String password);

}
