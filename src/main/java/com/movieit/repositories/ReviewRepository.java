package com.movieit.repositories;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movieit.models.Review;

public interface ReviewRepository extends JpaRepository <Review,Review> {

	@Query("SELECT review_body , review_date, user_email FROM Review WHERE movie_id=:movie_id")
	public List<Object[]> findReviewsforMovie(@Param("movie_id") Integer movie_id);
	
	
	
	
	
}