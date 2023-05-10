package com.onenzero.ozerp.core.entity;

import com.onenzero.ozerp.core.util.CommonUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	//Expiration time 10 minutes
	private static final Long EXPIRATION_TIME = 600000L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String token;
	private Long expirationTime;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
	private AppUser user;

	public VerificationToken(AppUser user, String token) {
		this.user = user;
		this.token = token;
		this.expirationTime = CommonUtils.timeStampGenerator() + EXPIRATION_TIME;
	}

	public VerificationToken(String token) {
		this.token = token;
		this.expirationTime = CommonUtils.timeStampGenerator() + EXPIRATION_TIME;
	}
}