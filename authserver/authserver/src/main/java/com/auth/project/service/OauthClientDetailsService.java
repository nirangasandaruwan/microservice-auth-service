package com.auth.project.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import com.auth.project.model.OauthClientDetails;
import com.auth.project.repository.OauthClientDetailsDao;


@Service("cliente")
public class OauthClientDetailsService  implements  ClientDetailsService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OauthClientDetailsDao oauthClientDetailsDao;

	

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

		logger.info("ClientDetailService - Clientes");
		OauthClientDetails client = oauthClientDetailsDao.findByClientId(clientId);
		if (client == null) {
			throw new ClientRegistrationException(String.format("Cliente ID no existe", clientId));
		}
		BaseClientDetails details = new BaseClientDetails();
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
		details.setAccessTokenValiditySeconds(client.getAccess_token_validity());
		details.setAuthorizedGrantTypes(Arrays.asList(client.getAuthorized_grant_types().split(",")));
		details.setClientId(client.getId());
		details.setClientSecret(client.getClient_secret());
		details.setRefreshTokenValiditySeconds(client.getRefresh_token_validity());
		details.setRegisteredRedirectUri(Collections.singleton(client.getWeb_server_redirect_uri()));
		details.setResourceIds(Arrays.asList(client.getResource_ids()));
		details.setScope(Arrays.asList(client.getScope()));
		logger.info("ClientDetailService - /Clientes");
		return details;
	}
	

}
