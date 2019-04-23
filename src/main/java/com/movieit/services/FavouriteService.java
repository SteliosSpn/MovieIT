package com.movieit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieit.models.Favourite;
import com.movieit.repositories.FavouriteRepository;


@Service
public class FavouriteService {

	@Autowired
	private FavouriteRepository favouriteRepository;
	
	public boolean findIfFavourite(Favourite favourite){
		Optional<Favourite> favouriteList=favouriteRepository.findById(favourite);
		//System.out.println(rating.getEmail());
				//System.out.println(rating.getMovie_id());
		if(favouriteList.isPresent()){
			//System.out.println("true");
			return true;}
		else{ //System.out.println("false");
			return false;}
		
		
	}
	
/*	public List <Favourite> findByEmail(String email){
		Optional<Favourite> favouriteList=favouriteRepository.f
		l
		return list;
	}*/
}
