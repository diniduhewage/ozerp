package com.onenzero.ozerp.core.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtility implements Serializable {

    private static final long serialVersionUID = 234234523523L;

    public static final long JWT_TOKEN_VALIDITY = 7 * 86400;
    public static final String PERMISSIONS = "permissions";
    public static final String ROLES = "roles";
    public static final String LOGGED_ROLE = "loggedRole";

    private final SecretKey key;

    public JWTUtility(@Value("${jwt.secret}") String secret) {

        byte[] secretBytes = secret.getBytes();
        key = new SecretKeySpec(secretBytes, 0, secretBytes.length, "HmacSHA512");
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails, List<String> roleList) {

        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails, roleList);
    }

    private String doGenerateToken(Map<String, Object> claims, UserDetails userDetails, List<String> roleList) {

        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .claim(ROLES, roleList)
                .claim(PERMISSIONS, userDetails.getAuthorities())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .setIssuer("OnceNZeros")
                .signWith(key, SignatureAlgorithm.HS512).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}