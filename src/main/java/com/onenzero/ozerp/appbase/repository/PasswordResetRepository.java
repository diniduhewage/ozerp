package com.onenzero.ozerp.appbase.repository;

import com.onenzero.ozerp.appbase.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(String token);

	PasswordResetToken findByUserId(Long id);

}