package com.auth.project.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auth.project.model.OauthClientDetails;

public interface OauthClientDetailsDao extends JpaRepository<OauthClientDetails, String>{

	@Query( "FROM OauthClientDetails oauthCD WHERE oauthCD.client_id =:id" )
	OauthClientDetails findByClientId( @Param("id")  String id);

	@Query( "FROM OauthClientDetails oauthCD WHERE oauthCD.resource_ids =:resource_ids" )
	List<OauthClientDetails> findByResourceId(@Param("resource_ids") String resource_ids);
 

	
}
