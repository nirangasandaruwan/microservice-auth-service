package com.auth.project.config.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.auth.project.model.AuthUserDetails;
import com.auth.project.model.User;
import com.auth.project.repository.UserDetailsRepository;

public class CustomDaoAuthenticationProvider implements AuthenticationProvider {

	Logger logger = LoggerFactory.getLogger(CustomDaoAuthenticationProvider.class);

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	private BCryptPasswordEncoder bcrypt;


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		List<GrantedAuthority> authorities = new ArrayList<>();

		User user = userDetailsRepository.getUserByUserName(username);
		
		Authentication auth;

		if (user == null) {
			throw new UsernameNotFoundException(String.format("User does not exist", username));
		}

		if (user != null) {
			if (!user.isEnabled()) {
				throw new DisabledException("The user is disabled");
			}
			if (!bcrypt.matches(password, user.getPassword())) {
				logger.debug("password",user.getPassword());
				
				throw new BadCredentialsException("Wrong password");
				
				
			}
			
			AuthUserDetails userDetails = new AuthUserDetails(user);
			
			userDetails.getRoles().forEach(role -> {
	          
	            role.getPermissions().forEach(permission -> {
	            	authorities.add(new SimpleGrantedAuthority(permission.getName()));
	            });

	        });
			
			
		}

		auth = new UsernamePasswordAuthenticationToken(username, password, authorities);
		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
