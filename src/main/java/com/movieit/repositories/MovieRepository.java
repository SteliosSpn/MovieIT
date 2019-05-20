package com.movieit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.movieit.models.Movie;

public interface MovieRepository  extends JpaRepository<Movie, Integer> {
	List<Movie> findByNameLike(String name);
	Movie findByName(String name);

	@Query("select max(movie_id) from Movie")
    Integer maxprimkey();
}