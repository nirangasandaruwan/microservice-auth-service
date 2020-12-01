package com.auth.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auth.project.model.User;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Integer> {
	
	//Optional<User> findByUsername(String username);
	
	
	@Query("SELECT u"
			+ " FROM User u"
			+ " WHERE u.username = :username"
	)
	User getUserByUserName( @Param("username") String username);

}
