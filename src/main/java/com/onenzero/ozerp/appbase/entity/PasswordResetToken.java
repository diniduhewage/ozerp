package com.onenzero.ozerp.appbase.entity;

import com.onenzero.ozerp.appbase.util.SaasUtil;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "password_reset_token_tab")
public class PasswordResetToken {
	//Expiration time 10 minutes
	private static final Long EXPIRATION_TIME = 600000L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String token;
	private Long expirationTime;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_PASSWORD_RESET_TOKEN"))
	private AppUser user;

	public PasswordResetToken(AppUser user, String token) {
		this.user = user;
		this.token = token;
		this.expirationTime = SaasUtil.timeStampGenerator() + EXPIRATION_TIME;
	}

	public PasswordResetToken(String token) {
		this.token = token;
		this.expirationTime = SaasUtil.timeStampGenerator() + EXPIRATION_TIME;
	}

}