package com.movieit.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.movieit.models.Favourite;
import com.movieit.models.Movie;
import com.movieit.repositories.FavouriteRepository;
import com.movieit.repositories.MovieRepository;
import com.movieit.services.MovieService;
import com.movieit.services.UserService;

//import com.chicago.models.Log;
//import com.chicago.services.UserService;

@Controller
public class ProfileController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FavouriteRepository favouriteRepo;
	
	@Autowired 
	private MovieRepository movieRepo;
	
	@GetMapping("/profile")
	public String showProfilePage(HttpSession session,Model profilemodel, Principal principal, 
			@RequestParam(defaultValue="")  String activity) {
		
		profilemodel.addAttribute("movie", new Movie());
		List<Movie> showMovies = new ArrayList<Movie>();
		String email=(String)session.getAttribute("email");
		Favourite favourite=new Favourite();
		favourite.setUser_email(email);
		List<Integer> favouriteList=favouriteRepo.findFavourites(email);
		for( Integer favmovies : favouriteList){
			//System.out.println(favmovies);
			Optional<Movie> tempMovie = movieRepo.findById(favmovies);
			if(tempMovie.isPresent()){
				//System.out.println(tempMovie);
				//showMovies.add(tempMovie);
				Movie movie = tempMovie.get();
				showMovies.add(movie);
				}
			//showMovies.add(movieRepo.findById(favmovies));
			
		}
		profilemodel.addAttribute("profilemovies", showMovies);
		
		
		
		return "views/profile";
	}
	
	@PostMapping("/removeFromFavouritesPR")
 	public String removeFromFavourites(HttpSession session,@Valid @ModelAttribute("movie") Movie movie,BindingResult bindingResult,
 			Model profilemodel) {
		if(bindingResult.hasErrors()) {
			return "views/profile";
		}
		//Movie myMovie = (Movie) session.getAttribute("movie");
		
		String email = (String) session.getAttribute("email");
		Favourite favourite = new Favourite();
		favourite.setMovie_id(movie.getMovie_id());
		favourite.setUser_email(email);
		System.out.println(movie.getName());
		//favouriteRepo.delete(favourite);

		//redirect:/index.html
		return "redirect:/profile";
		//return "views/movie";
	}

}
