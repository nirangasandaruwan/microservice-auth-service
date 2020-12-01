package com.auth.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.project.model.AuthUserDetails;
import com.auth.project.model.User;
import com.auth.project.repository.UserDetailsRepository;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

	Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		logger.debug("loadUserByUsername - username - " + username);
		User user =  userDetailsRepository.getUserByUserName(username);
		
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User does not exist", username));
		}
		
	
		
		logger.debug("usernameeeeee" + user.getUsername());
		
		UserDetails userDetails = new AuthUserDetails(user);
		return userDetails;
	}

}
