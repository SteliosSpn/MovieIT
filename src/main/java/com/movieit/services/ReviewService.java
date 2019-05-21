package com.movieit.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import com.movieit.models.Movie;
import com.movieit.models.Review;
import com.movieit.repositories.MovieRepository;
import com.movieit.repositories.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private MovieRepository movieRepo;
	
	@Cacheable("Reviews")
	public List<Movie> showReviews(String email){
		List<Object[]> reviewList=reviewRepository.findReviewsforUser(email);
		 List<Review> finalReviewList=new ArrayList<Review>();
		 for(Object[] review1:reviewList){
			 String review_body = (String)review1[0];
			
			 Date review_date = (Date)review1[1];
			
			 Integer movie_id = (Integer)review1[2];
			
			 Review review = new Review();
			 review.setMovie_id(movie_id);
			 review.setReview_body(review_body);
			 review.setReview_date(review_date);
			 
			 review.setUser_email(email);
			 finalReviewList.add(review);
		 }
		
		 List<Movie> reviewListforMovies=new ArrayList<Movie>();
		for(Review review:finalReviewList){
			
			Optional<Movie> tempMovie = movieRepo.findById(review.getMovie_id());
			if(tempMovie.isPresent()){
				
				Movie movie = tempMovie.get();
				movie.setDescription(review.getReview_body());
				movie.setImage_url(movie.getImage_url().replace(".\\src\\main\\resources\\static\\images\\", "/images/"));
				System.out.println(movie.getImage_url());
				reviewListforMovies.add(movie);
				}
		}
		return reviewListforMovies;
	}
	
}