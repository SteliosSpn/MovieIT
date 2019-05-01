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
import com.movieit.models.User;
import com.movieit.repositories.FavouriteRepository;
import com.movieit.repositories.MovieRepository;
import com.movieit.repositories.UserRepository;
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
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/profile")
	public String showProfilePage(HttpSession session,Model profilemodel, Principal principal, 
			@RequestParam(defaultValue="")  String activity) {
		
		profilemodel.addAttribute("movie", new Movie());
		//profilemodel.addAttribute("user",new User());
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
		
		User user =new User();
		Optional <User> userlist = userRepo.findById(email);
		if(userlist.isPresent()){
			user=userlist.get();
		}
		profilemodel.addAttribute("user",user);
		
		
		return "views/profile";
	}
	
	@PostMapping("/removeFromFavouritesPR")
 	public String removeFromFavourites(HttpSession session,@Valid @ModelAttribute("movie") Movie movie,BindingResult bindingResult,
 			Model profilemodel) {
		if(bindingResult.hasErrors()) {
			return "redirect:/profile";
		}
		//Movie myMovie = (Movie) session.getAttribute("movie");
		
		String email = (String) session.getAttribute("email");
		Favourite favourite = new Favourite();
		favourite.setMovie_id(movie.getMovie_id());
		favourite.setUser_email(email);
		System.out.println(movie.getMovie_id());
		favouriteRepo.delete(favourite);

		//redirect:/index.html
		return "redirect:/profile";
		//return "views/movie";
	}
	
	@PostMapping("/editProfile")
 	public String editProfile(HttpSession session,@Valid @ModelAttribute("user") User user,BindingResult bindingResult, Model profilemodel) {
		if(bindingResult.hasErrors()) {
			//return "redirect:/profile";
		}
		String email = (String) session.getAttribute("email");
		User dbuser =new User();
		Optional <User> userlist = userRepo.findById(email);
		if(userlist.isPresent()){
			dbuser=userlist.get();
		}
		//System.out.println(dbuser.getPassword());
		
		user.setEmail(email);
		user.setUsername(dbuser.getUsername());
		user.setPassword(dbuser.getPassword());
	
		//userRepo.save(user);
			return "redirect:/profile";
	}

}
