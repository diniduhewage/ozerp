package com.onezero.ozerp.repository;

import com.onezero.ozerp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT DISTINCT r.code FROM ((User u "
            + "INNER JOIN UserRole ur ON u.id = ur.userId) "
            + "INNER JOIN Role r ON r.id = ur.roleId)  WHERE u.email = :email")
    public List<String> findRoleCodesByUser(@Param("email") String email);

}