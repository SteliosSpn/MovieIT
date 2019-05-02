package com.movieit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieit.models.User;

public interface UserRepository  extends JpaRepository<User, String> {

	

}
