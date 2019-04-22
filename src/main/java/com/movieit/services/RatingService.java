package com.movieit.services;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieit.models.Rating;
import com.movieit.repositories.RatingRepository;

@Service
public class RatingService {
	@Autowired
	private RatingRepository ratingRepository;
	
	public boolean findIfAlreadyRated(Rating rating){
		Optional<Rating> ratingList=ratingRepository.findById(rating);
		//System.out.println(rating.getEmail());
				//System.out.println(rating.getMovie_id());
		if(ratingList.isPresent()){
			//System.out.println("true");
			return true;}
		else{ //System.out.println("false");
			return false;}
		
		
	}
}
