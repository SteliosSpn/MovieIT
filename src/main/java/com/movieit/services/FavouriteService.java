package com.movieit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.movieit.models.Favourite;
import com.movieit.models.Movie;
import com.movieit.repositories.FavouriteRepository;
import com.movieit.repositories.MovieRepository;


@Service
public class FavouriteService {

	@Autowired
	private FavouriteRepository favouriteRepository;
	
	@Autowired
	private MovieRepository movieRepo;
	
	public boolean findIfFavourite(Favourite favourite){
		Optional<Favourite> favouriteList=favouriteRepository.findById(favourite);
		
		if(favouriteList.isPresent()){
			
			return true;}
		else{ 
			return false;}
		
		
	}
	
	@Cacheable("Favourites")
	public List<Movie> showFavouriteMovies(String email){
		List<Movie> showMovies = new ArrayList<Movie>();
		Favourite favourite=new Favourite();
		favourite.setUser_email(email);
		List<Integer> favouriteList=favouriteRepository.findFavourites(email);
		for( Integer favmovies : favouriteList){
			
			Optional<Movie> tempMovie = movieRepo.findById(favmovies);
			if(tempMovie.isPresent()){
				Movie movie = tempMovie.get();
				
				movie.setImage_url(movie.getImage_url().replace(".\\src\\main\\resources\\static\\images\\", "/images/"));
				showMovies.add(movie);
				}
		
			
			
		}
		
		return showMovies;
	}
	

}
