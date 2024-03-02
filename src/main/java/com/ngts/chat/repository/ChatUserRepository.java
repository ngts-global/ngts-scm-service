package com.ngts.chat.repository;

import com.ngts.chat.entity.ChatUserEntity;
import com.ngts.chat.entity.enum1.EChatRegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    Optional<List<ChatUserEntity>> findAllUsersByEmailAndPin(@Param("email") String email, @Param("pin") Integer pin);
    @Query(nativeQuery = true, value = "SELECT * FROM chat_users a WHERE a.chat_id = :chatId AND a.pin = :pin")
    Optional<List<ChatUserEntity>> findByChatIdAndPin(String chatId, Integer pin);

}
