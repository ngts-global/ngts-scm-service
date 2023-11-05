package com.ngts.auth.repository;

import com.ngts.auth.entity.ERole;
import com.ngts.auth.entity.Roles;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface RolesRepository extends CrudRepository<Roles, Integer> {

    Optional<Roles> findByName(ERole name);
}