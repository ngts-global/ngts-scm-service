package com.ngts.auth.repository;

import com.ngts.auth.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface UserRolesRepository extends JpaRepository<UserRoles, Integer>, JpaSpecificationExecutor<UserRoles> {

    Optional<UserRoles> findByUserId(Integer userId);
}