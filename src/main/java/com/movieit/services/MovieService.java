package com.movieit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieit.models.Movie;
import com.movieit.repositories.MovieRepository;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	public void createMovie(Movie movie) {
	    movie.setTotal_score(0);
	    movie.setTotal_votes(0);
		movieRepository.save(movie);
	}
	
	public List<Movie> findByMovieName(String name) {
		// TODO Auto-generated method stub
		return  movieRepository.findByNameLike("%"+name+"%");
	}
	
	public List<Movie> findByName(String name){
		return movieRepository.findByName(name);
	}
	
}
