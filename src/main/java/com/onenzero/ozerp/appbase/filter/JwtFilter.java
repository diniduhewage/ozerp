package com.onenzero.ozerp.appbase.filter;

import com.onenzero.ozerp.appbase.service.impl.UserServiceImpl;
import com.onenzero.ozerp.appbase.util.JWTUtility;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserServiceImpl userServiceImpl;
    
    public static final String LOGGED_ROLE = "loggedRole";

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
									FilterChain filterChain) throws ServletException, IOException {
		String authorization = httpServletRequest.getHeader("Authorization");
		List<String> roleList = null;
		String token = null;
		String userName = null;
      
		if (null != authorization && authorization.startsWith("Bearer ")) {
			token = authorization.substring(7);
			userName = jwtUtility.getUsernameFromToken(token);
			Claims allClaimsFromToken = jwtUtility.getAllClaimsFromToken(token);
			roleList = (List<String>) allClaimsFromToken.get("roles");
			logger.info("logged role: " + roleList);
		}

		if (null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userServiceImpl.getUserDetails(userName, roleList);

			if (jwtUtility.validateToken(token, userDetails)) {

				for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
					System.out.println(grantedAuthority.getAuthority());
				}
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
			
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			} 

		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}