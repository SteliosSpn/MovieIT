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
import com.movieit.repositories.TagRepository;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	public void createMovie(Movie movie, String action, String comedy, String drama, String romance, String horror , String crime, String adventure , String animation, String scifi,String thriller,String mystery) {
	    movie.setTotal_score(0);
	    movie.setTotal_votes(0);
	    //tags..
	    List<Tag> movieTags = new ArrayList<>();
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
	    if(romance.equals("on")) {
	    	Tag tag = new Tag("Romance");
	    	movieTags.add(tag);
	    }
	    if(horror.equals("on")) {
	    	Tag tag = new Tag("Horror");
	    	movieTags.add(tag);
	    }
	    if(adventure.equals("on")) {
	    	Tag tag = new Tag("Adventure");
	    	movieTags.add(tag);
	    }
	    if(animation.equals("on")) {
	    	Tag tag = new Tag("Animation");
	    	movieTags.add(tag);
	    }
	    if(scifi.equals("on")) {
	    	Tag tag = new Tag("SciFi");
	    	movieTags.add(tag);
	    }
	    if(thriller.equals("on")) {
	    	Tag tag = new Tag("Thriller");
	    	movieTags.add(tag);
	    }
	    if(mystery.equals("on")) {
	    	Tag tag = new Tag("Mystery");
	    	movieTags.add(tag);
	    }
	    if(crime.equals("on")) {
	    	Tag tag = new Tag("Crime");
	    	movieTags.add(tag);
	    }
	  
		movieRepository.save(movie);
		
		for(Tag tag: movieTags) {
	    	tag.setMovie_id(movieRepository.maxprimkey());
	    	tagRepository.save(tag);
	    }
	}
	
	public List<Movie> findRelatedMovies(List<String> mytags, Integer myMovie_id){
		HashMap<Movie,Double> values = new HashMap<Movie,Double>();
		for(Movie movie: movieRepository.findAll()) {
			if(movie.getMovie_id() != myMovie_id) {
			Integer count = 0;
			for(String tag: tagRepository.findTags(movie.getMovie_id())) {
				if(mytags.contains(tag)) {
					count++;
				}
			}
			if(count > 0) {
				if(movie.getTotal_votes() != 0) {
					DecimalFormat df = new DecimalFormat();
					 df.setRoundingMode(RoundingMode.DOWN);
				values.put(movie, count + (Double.parseDouble(
						df.format((double)movie.getTotal_score()/(double)movie.getTotal_votes()))*0.1));
				}else {
					movie.setImage_url(movie.getImage_url().replace(".\\src\\main\\resources\\static\\images\\", "/images/"));
					values.put(movie, Double.valueOf(count));
				}
			}
		}
		}
		System.out.println(values);
		return sortByValueAndGetTop5(values);	
	}
	
	public List<Movie> sortByValueAndGetTop5(HashMap<Movie,Double> unsortMap) {
		Map<Movie, Double> result = new LinkedHashMap<>();
        unsortMap.entrySet().stream()
                .sorted(Map.Entry.<Movie, Double>comparingByValue().reversed())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
        
        List<Movie> sorted = new ArrayList<Movie>(result.keySet());
        List<Movie> top = sorted.stream().limit(5).collect(Collectors.toList());
        return top;
	}

	public List<Movie> findByMovieName(String name) {
		// TODO Auto-generated method stub
		return  movieRepository.findByNameLike("%"+name+"%");
	}
	
	public Movie findByName(String name){
		return movieRepository.findByName(name);
	}
	
}