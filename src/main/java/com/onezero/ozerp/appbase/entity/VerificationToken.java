package com.onezero.ozerp.appbase.entity;

import com.onezero.ozerp.appbase.util.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken implements Serializable {
    private static final long serialVersionUID = 1L;
    //Expiration time 10 minutes
    private static final Long EXPIRATION_TIME = 600000L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Long expirationTime;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public VerificationToken(User user, String token) {
        this.user = user;
        this.token = token;
        this.expirationTime = CommonUtils.timeStampGenerator() + EXPIRATION_TIME;
    }

    public VerificationToken(String token) {
        this.token = token;
        this.expirationTime = CommonUtils.timeStampGenerator() + EXPIRATION_TIME;
    }

}