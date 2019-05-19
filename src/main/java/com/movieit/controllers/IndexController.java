package com.movieit.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.movieit.models.Movie;
import com.movieit.services.MovieService;

@Controller
public class IndexController {
	
	@Autowired
	MovieService ms;
	
	@GetMapping("/index")
	public String showIndexPage(HttpSession session,Principal principal,Model model, @RequestParam(defaultValue="Nothing")  String movie_name) {
		List<Movie> showMovies = new ArrayList<Movie>();
		for(Movie movie: ms.findByMovieName(movie_name)) {
			movie.setImage_url(movie.getImage_url().replace(".\\src\\main\\resources\\static\\images\\", "/images/"));
			showMovies.add(movie);
		}
		String email = principal.getName();
		session.setAttribute("email", email);
		model.addAttribute("movies", showMovies);
		return "views/index";  
	}
	
	
	
	@GetMapping("/login") 
	public String showLoginForm() {
		
		return "views/loginForm";  
	}
	
	
	  
	

}
