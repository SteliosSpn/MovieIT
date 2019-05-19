package com.movieit.services;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieit.models.Movie;
import com.movieit.models.Tag;
import com.movieit.repositories.MovieRepository;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	public void createMovie(Movie movie, String action, String comedy, String drama, String romantic, String horror) {
	    movie.setTotal_score(0);
	    movie.setTotal_votes(0);
	    //tags..
	    List<Tag> movieTags = new ArrayList<Tag>();
	    if(action.equals("on")) {
	    	Tag tag = new Tag("Action");
	    	movieTags.add(tag);
	    }
	    if(comedy.equals("on")) {
	    	Tag tag = new Tag("Comedy");
	    	movieTags.add(tag);
	    }
	    if(drama.equals("on")) {
	    	Tag tag = new Tag("Drama");
	    	movieTags.add(tag);
	    }
	    if(romantic.equals("on")) {
	    	Tag tag = new Tag("Romantic");
	    	movieTags.add(tag);
	    }
	    if(horror.equals("on")) {
	    	Tag tag = new Tag("Horror");
	    	movieTags.add(tag);
	    }
	    
	    movie.setTags(movieTags);
		movieRepository.save(movie);
	}
	
	public List<Movie> findRelatedMovies(List<Tag> mytags){
		HashMap<Movie,Double> values = new HashMap<Movie,Double>();
		for(Movie movie: movieRepository.findAll()) {
			Integer count = 0;
			for(Tag tag: movie.getTags()) {
				if(mytags.contains(tag)) {
					count++;
				}
			}
			if(movie.getTotal_votes() != 0) {
				 DecimalFormat df = new DecimalFormat();
				 df.setRoundingMode(RoundingMode.DOWN);
			values.put(movie, count + Double.parseDouble(
					df.format((double)movie.getTotal_score()/(double)movie.getTotal_votes())));
			}
		}
		//sortByValue(values);
		return null;
	}
	/*
	public HashMap<Movie,Double> sortByValueAndGetTop5(HashMap<Movie,Double> unsortMap) {
		Map<Movie, Double> result = new LinkedHashMap<>();
        unsortMap.entrySet().stream()
                .sorted(Map.Entry.<Movie, Double>comparingByValue().reversed())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
        return result;
        
        List<Movie> keys = unsortMap.entrySet().stream()
        		  .map(Map.Entry::getKey)
        		  .sorted()
        		  .limit(10)
        		  .collect(Collectors.toList());
	}
	*/
	public List<Movie> findByMovieName(String name) {
		// TODO Auto-generated method stub
		return  movieRepository.findByNameLike("%"+name+"%");
	}
	
	public Movie findByName(String name){
		return movieRepository.findByName(name);
	}
	
}