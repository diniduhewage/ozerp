package com.onezero.ozerp.appbase.filter;

import com.onezero.ozerp.appbase.util.JWTUtility;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;


@Component
public class JwtFilter extends OncePerRequestFilter {

    public static final String LOGGED_ROLE = "loggedRole";
    private final SecretKey key;
    @Autowired
    private JWTUtility jwtUtility;

    public JwtFilter(@Value("${jwt.secret}") String secret) {

        byte[] secretBytes = secret.getBytes();
        key = new SecretKeySpec(secretBytes, 0, secretBytes.length, "HmacSHA512");
    }

    public static String decodeBase64CompressedToken(String base64CompressedToken) throws IllegalArgumentException {
        try {
            byte[] compressedData = DatatypeConverter.parseBase64Binary(base64CompressedToken);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedData);
            GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8));

            StringBuilder tokenBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                tokenBuilder.append(line);
            }

            return tokenBuilder.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to decode base64 compressed token string: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        List<String> roleList = null;
        String token = null;
        boolean isUserEnabled = false;
        String userName = null;
        List<Map<String, String>> userDetailMapList = null;

        if (null != authorization && authorization.startsWith("Bearer ")) {

            token = authorization.substring(7);
            token = decodeBase64CompressedToken(token);
            userName = jwtUtility.getUsernameFromToken(token);
            Claims allClaimsFromToken = jwtUtility.getAllClaimsFromToken(token);
            roleList = (List<String>) allClaimsFromToken.get("roles");
            isUserEnabled = allClaimsFromToken.get("isEnable") == null ? false
                    : (boolean) allClaimsFromToken.get("isEnable");
            userDetailMapList = (List<Map<String, String>>) allClaimsFromToken.get("permissions");
            logger.info("logged role: " + roleList);
            logger.info("logged userDetailMapList: " + userDetailMapList);
            if (!isUserEnabled) {
                throw new DisabledException("User account is disabled");
            }
        }

        if (null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {
            //UserDetails userDetails = userServiceImpl.getUserDetails(userName, roleList);

            UserDetails userDetails = getUserDetails(userDetailMapList, userName);
            if (jwtUtility.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private UserDetails getUserDetails(List<Map<String, String>> userDetailMapList, String userName) {
        List<String> authoritiesList = userDetailMapList.stream().map(map -> map.get("authority"))
                .collect(Collectors.toList());

        Set<SimpleGrantedAuthority> authoritiesSet = new HashSet<>();
        for (String authority : authoritiesList) {
            authoritiesSet.add(new SimpleGrantedAuthority(authority));
        }
        return new org.springframework.security.core.userdetails.User(userName, "", true, true, true,
                true, authoritiesSet);
    }

}