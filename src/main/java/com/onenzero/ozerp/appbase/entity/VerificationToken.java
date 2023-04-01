package com.onenzero.ozerp.appbase.entity;

import com.onenzero.ozerp.appbase.util.SaasUtil;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "verification_token_tab")
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
		this.expirationTime = SaasUtil.timeStampGenerator()+EXPIRATION_TIME;
	}
	
	public VerificationToken(String token) {
		this.token = token;
		this.expirationTime = SaasUtil.timeStampGenerator()+EXPIRATION_TIME;
	}
}