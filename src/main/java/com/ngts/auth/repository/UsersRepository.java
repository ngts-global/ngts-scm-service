package com.ngts.auth.repository;

import com.ngts.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer>, JpaSpecificationExecutor<Users> {

    Optional<Users> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<List<Users>> findAllUsersByEmailAndPassword(@Param("email") String email, @Param("password") String password);
   // @Query("SELECT * FROM users u WHERE email = :email AND password = :password")
    //
}