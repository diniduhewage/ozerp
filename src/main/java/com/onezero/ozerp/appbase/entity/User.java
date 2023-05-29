package com.onezero.ozerp.appbase.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @Column(length = 60)
    private String password;
    private boolean enabled = false;
    private Long createdDate;
    private Long modifiedDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserRole> userRoles;

    private String phone;
    private String address;
    private String dateOfBirth;
    private String gender;
    private String nationality;
    private String userType;
    private String username;
    private String status;
    private Long lastLoginDate;
    private Long lastLogoutDate;
    private Long accountCreationDate;
    private Long accountExpirationDate;
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] profilePicture;
    private String securityQuestion1;
    private String securityAnswer1;
    private String securityQuestion2;
    private String securityAnswer2;
    private boolean twoFactorAuthentication;
    private Long lastTwoFactorAuthenticationDate;
    private String preferredLanguage;
    private String timezone;
    private boolean notifications;
    private String notificationSettings;
    // private UserAccessLog accessLogs;

    public User(Long id) {
        this.id = id;
    }

}
