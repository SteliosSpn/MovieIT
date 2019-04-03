package com.movieit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieit.models.Movie;

public interface MovieRepository  extends JpaRepository<Movie, Integer> {
	List<Movie> findByNameLike(String name);
	List<Movie> findByName(String name);

}