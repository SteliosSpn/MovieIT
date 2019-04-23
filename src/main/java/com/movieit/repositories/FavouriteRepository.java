package com.movieit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movieit.models.Favourite;

public interface FavouriteRepository extends JpaRepository <Favourite,Favourite> {

	@Query("SELECT movie_id FROM Favourite WHERE user_email=:email")
	public List <Integer> findFavourites(@Param("email") String email);
}
