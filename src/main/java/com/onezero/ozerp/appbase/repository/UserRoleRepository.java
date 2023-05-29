package com.onezero.ozerp.appbase.repository;

import com.onezero.ozerp.appbase.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}