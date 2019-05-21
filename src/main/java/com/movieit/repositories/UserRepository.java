package com.movieit.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import com.movieit.models.User;

public interface UserRepository  extends JpaRepository<User, String> {

	

}
