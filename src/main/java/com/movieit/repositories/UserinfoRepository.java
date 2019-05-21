package com.movieit.repositories;





import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movieit.models.Userinfo;

public interface UserinfoRepository  extends JpaRepository<Userinfo, String> {

	@Query("SELECT username FROM Userinfo WHERE email=:email")
	public String getUsername(@Param("email") String email);
	
	@Query("SELECT email FROM Userinfo WHERE username=:username")
	public String getEmail(@Param("username") String username);

}