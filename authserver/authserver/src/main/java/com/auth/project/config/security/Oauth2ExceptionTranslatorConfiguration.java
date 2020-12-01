package com.auth.project.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

@Configuration
public class Oauth2ExceptionTranslatorConfiguration {

	@Bean
	public WebResponseExceptionTranslator<OAuth2Exception> oauth2ResponseExceptionTranslator() {
		return new DefaultWebResponseExceptionTranslator() {

			@Override
			public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
				e.printStackTrace();

				ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
				OAuth2Exception body = responseEntity.getBody();
				HttpStatus statusCode = responseEntity.getStatusCode();

				body.addAdditionalInformation("codeHttp", "401");
				body.addAdditionalInformation("code", "401");
				body.addAdditionalInformation("body", body.getMessage());
				body.addAdditionalInformation("message", body.getMessage());
				body.addAdditionalInformation("state", "false");

				HttpHeaders headers = new HttpHeaders();
				headers.setAll(responseEntity.getHeaders().toSingleValueMap());
				return new ResponseEntity<>(body, headers, statusCode);
			}
		};
	}

}
