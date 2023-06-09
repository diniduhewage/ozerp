package com.onezero.ozerp.appbase.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.onezero.ozerp.appbase.dto.ApiResponseDto;
import com.onezero.ozerp.appbase.error.ApiError;
import com.onezero.ozerp.appbase.error.exception.AccessDeniedException;
import com.onezero.ozerp.appbase.error.exception.BadRequestException;
import com.onezero.ozerp.appbase.util.JWTUtility;
import com.onezero.ozerp.enterprise.config.CustomHttpServletRequestWrapper;
import com.onezero.ozerp.enterprise.config.TenantApiKeyConfig;
import com.onezero.ozerp.enterprise.dto.TenantDTO;

import io.jsonwebtoken.Claims;


@Component
public class JwtFilter extends OncePerRequestFilter {
	
	private final static String UNAUTHORIZE_MESSAGE = "Unathorized Access";

    @Autowired
    private JWTUtility jwtUtility;
    
    @Autowired
    private TenantApiKeyConfig tenantApiKeyConfig;
    
    private ObjectMapper objectMapper;

    private final SecretKey key;
    

	public JwtFilter(@Value("${jwt.secret}") String secret) {

		byte[] secretBytes = secret.getBytes();
		key = new SecretKeySpec(secretBytes, 0, secretBytes.length, "HmacSHA512");
		objectMapper = new ObjectMapper();
	}
    public static final String LOGGED_ROLE = "loggedRole";

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		String requestBody = null;
		String requestUri = httpServletRequest.getRequestURI();
		String xAuthorization = httpServletRequest.getHeader("x-Authorization");
		String authorization = httpServletRequest.getHeader("Authorization");
		TenantDTO tenantDTO = null;
		try {
			if (xAuthorization == null && !xAuthExcludeEndpoint(requestUri)) {
				throw new BadRequestException("Please provide x-Authorization");
			} else if (!xAuthExcludeEndpoint(requestUri)) {
				
		        requestBody = getRequestBody(httpServletRequest);

		        JsonNode jsonNode = objectMapper.readTree(requestBody);

		        JsonNode tenantDtoNode = jsonNode.get("tenantDTO");
		       
		        tenantDTO = objectMapper.treeToValue(tenantDtoNode, TenantDTO.class);
		        if (tenantDTO == null) {
		        	throw new BadRequestException("Please provide tenantDTO!");
		        }
		        if (tenantDTO.getId() == null) {
		        	throw new BadRequestException("Please provide tenantId!");
		        }
		        	
			}
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

				// x-authorize validation
				
				
//				Object tenantIdListObj = allClaimsFromToken.get("tenantIdList");
//				System.out.println("Type of tenantIdListObj: " + tenantIdListObj.getClass().getName());
//
//				if (tenantIdListObj instanceof ArrayList<?>) {
//				    ArrayList<?> tenantIdList = (ArrayList<?>) tenantIdListObj;
//				    if (!tenantIdList.isEmpty()) {
//				        Object firstElement = tenantIdList.get(0);
//				        Class<?> elementType = firstElement.getClass();
//				        System.out.println("Element type of tenantIdList: " + elementType.getName());
//				    } else {
//				        System.out.println("The tenantIdList is empty.");
//				    }
//				} else {
//				    throw new IllegalStateException("tenantIdListObj is not an instance of ArrayList");
//				}

				ArrayList<Integer> tenantIdList = (ArrayList<Integer>) allClaimsFromToken.get("tenantIdList");
				if (xAuthorization != null && tenantIdList.isEmpty()) {
					throw new BadRequestException("Please register user with companies!");
				}
				
				if (xAuthorization != null && !xAuthorization.equals((String) allClaimsFromToken.get("tenantApiKey"))
						&& !xAuthExcludeEndpoint(requestUri)) {
					throw new BadRequestException("x-Authorization mismatched!");
				}
				
				
				
				boolean isContextUser = (boolean) allClaimsFromToken.get("isContextUser");
				if (xAuthorization != null && isContextUser
						&& !tenantIdList.contains(tenantDTO.getId().intValue())) {
					throw new BadRequestException("Invalid tenant id!");
				}
				
//				if (xAuthorization != null && tenantIdList.get(0) != tenantDTO.getId().intValue()) {
//					throw new BadRequestException("Invalid tenant id!");
//				}
				if (xAuthorization != null && isContextUser && tenantApiKeyConfig.getContextUserMapTenantCount(
						allClaimsFromToken.get("sub", String.class)) != Long.valueOf(tenantIdList.size())) {
                    throw new BadRequestException("Please log in again");
				}
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
				// UserDetails userDetails = userServiceImpl.getUserDetails(userName, roleList);

				UserDetails userDetails = getUserDetails(userDetailMapList, userName);
				if (jwtUtility.validateToken(token, userDetails)) {

					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

				}

			}
			if ((!xAuthExcludeEndpoint(requestUri))) {
				CustomHttpServletRequestWrapper requestWrapper = new CustomHttpServletRequestWrapper(httpServletRequest, requestBody);
				filterChain.doFilter(requestWrapper, httpServletResponse);
			} else {
				filterChain.doFilter(httpServletRequest, httpServletResponse);
			}
			
		} catch (BadRequestException e) {

			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
			ApiResponseDto responseData = new ApiResponseDto(false, apiError);
			String jsonResponse = convertObjectToJson(responseData);

			httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
			httpServletResponse.getWriter().write(jsonResponse);

			return;
		} catch (AuthenticationException | org.springframework.security.access.AccessDeniedException
				| AccessDeniedException e) {
		    ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, UNAUTHORIZE_MESSAGE);
		    ApiResponseDto responseData = new ApiResponseDto(false, apiError);
		    String jsonResponse = convertObjectToJson(responseData);

		    httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		    httpServletResponse.getWriter().write(jsonResponse);

		    return;
		} 

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
	        throw new AccessDeniedException("Failed to decode base64 compressed token string: " + e.getMessage(), e);
	    }
	}
	
	private boolean xAuthExcludeEndpoint(String requestUri) {
	    // Define the request URIs for which you want to exclude x-Authorization
	    List<String> excludedEndpoints = Arrays.asList(
	        "/register",
	        "/tenant",
	        "/tokenVerification",
	        "/resendVerifyToken",
	        "/resetPassword",
	        "/verifyRegistration",
	        "/reset/password",
	        "/changePassword",
	        "/swagger-ui/",
	        "/v3/api-docs/",
	        "/login",
			"/permissions",
			"/user-tenants"
	    );

	    for (String endpoint : excludedEndpoints) {
	        if (requestUri.contains(endpoint)) {
	            return true;
	        }
	    }

	    return false;
	}
	
	private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        return requestBody.toString();
    }


	private String convertObjectToJson(Object object) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper.writeValueAsString(object);
	}

}