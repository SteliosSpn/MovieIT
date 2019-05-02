package com.movieit.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movieit.models.Userinfo;

public interface UserinfoRepository  extends JpaRepository<Userinfo, String> {

	@Query("SELECT username FROM Userinfo WHERE email=:email")
	public String getUsername(@Param("email") String email);

}