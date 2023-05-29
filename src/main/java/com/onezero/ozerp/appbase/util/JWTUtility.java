package com.onezero.ozerp.appbase.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.zip.GZIPOutputStream;

@Component
public class JWTUtility implements Serializable {


    public static final long JWT_TOKEN_VALIDITY = 7 * 86400;
    public static final String PERMISSIONS = "permissions";
    public static final String ROLES = "roles";
    public static final String USER_STATUS = "isEnable";
    public static final String LOGGED_ROLE = "loggedRole";
    private static final long serialVersionUID = 234234523523L;
    private final Logger logger = LoggerFactory.getLogger(JWTUtility.class);

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

    public String generateToken(UserDetails userDetails, List<String> roleLits, boolean isUserEnabled) {

        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails, roleLits, isUserEnabled);
    }

    private String doGenerateToken(Map<String, Object> claims, UserDetails userDetails, List<String> roleLits, boolean isUserEnabled) {

        String token = Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .claim(ROLES, roleLits)
                .claim(PERMISSIONS, userDetails.getAuthorities())
                .claim(USER_STATUS, isUserEnabled)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .setIssuer("OnceNZeros")
                .signWith(key, SignatureAlgorithm.HS512).compact();


        String base64CompressedToken = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
            gzipOutputStream.write(token.getBytes(StandardCharsets.UTF_8));
            gzipOutputStream.close();

            byte[] compressedData = outputStream.toByteArray();
            return DatatypeConverter.printBase64Binary(compressedData);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return base64CompressedToken;

    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}