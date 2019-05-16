package com.movieit.repositories;



import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;



import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movieit.models.Review;

public interface ReviewRepository extends JpaRepository <Review,Review> {

	@Query("SELECT review_body , review_date, user_email FROM Review WHERE movie_id=:movie_id")
	public List<Object[]> findReviewsforMovie(@Param("movie_id") Integer movie_id);
	
	@Query("SELECT review_body , review_date, movie_id FROM Review WHERE user_email=:user_email")
	public List<Object[]> findReviewsforUser(@Param("user_email") String user_email);
	
	@Query("SELECT review_body , review_date FROM Review WHERE movie_id=:movie_id AND user_email=:user_email")
	public List<Review>findifReviewed(@Param("movie_id")Integer movie_id,@Param("user_email")String user_email);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE Review r set review_body=:review_body, review_date=:review_date WHERE r.user_email=:user_email"
			+ " AND r.movie_id=:movie_id")
	public Integer updateReview(@Param("movie_id")Integer movie_id,@Param("user_email")String user_email,
			@Param("review_body")String review_body,@Param("review_date")Date review_date);
	
	
	
}