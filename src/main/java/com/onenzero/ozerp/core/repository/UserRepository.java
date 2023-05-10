package com.onenzero.ozerp.core.repository;

import com.onenzero.ozerp.core.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
 

 
@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    boolean existsByUserName (String userName);
    
    @Query(nativeQuery = true, value = "SELECT DISTINCT r.code FROM ((user_tab u "
            + "INNER JOIN user_role_tab ur ON u.id = ur.user_id) "
            + "INNER JOIN role_tab r ON r.id = ur.role_id)  WHERE u.user_name = :userName")
    public List<String> findRoleCodesByUser(@Param("userName") String userName);

    AppUser findByUserName(String userName);
}