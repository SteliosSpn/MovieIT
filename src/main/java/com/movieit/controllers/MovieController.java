package com.movieit.controllers;

import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.movieit.models.Movie;
import com.movieit.models.Rating;
import com.movieit.models.User;
import com.movieit.repositories.MovieRepository;
import com.movieit.repositories.RatingRepository;
import com.movieit.services.MovieService;
import com.movieit.services.RatingService;
import com.movieit.services.UserService;

@Controller
public class MovieController {

	@Autowired
	private UserService us;
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private MovieRepository movierepo;
	
	@Autowired
	private RatingRepository ratingRepo;
	
	@Autowired
	private MovieService ms;
	
	private static final String path = "C:\\Users\\Stelios\\workspace\\MovieIT\\src\\main\\resources\\images\\";
	
	 @GetMapping("/addMovie")
	 public String addMovieForm(Model model, HttpSession session) {
		 model.addAttribute("movie", new Movie());
		 return "views/addMovie";
	 }
	 
	 @PostMapping("/addMovie")
	 	public String addMovieImpl(HttpSession session,@Valid @ModelAttribute("movie") Movie movie,BindingResult bindingResult,
	 			Model model,@RequestParam("file") MultipartFile image) {
			if(bindingResult.hasErrors()) {
				return "views/addMovie";
			}
			try {
				movie.setImage_url(path + image.getOriginalFilename());
		        Files.write(Paths.get(movie.getImage_url()), image.getBytes());
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
			System.out.println(movie.getImage_url());
			System.out.println(image.getOriginalFilename());
			ms.createMovie(movie);
			return "views/MovieSuccess";
		}
	
	 //th:href="@{/movie(id=${movie.name})}" || th:href="@{/movie/${movie.name}}" || session
	 @GetMapping("/movie/{name}")
	 public String movieHandler(Model model, HttpSession session, @PathVariable(value="name") String movie_name) {
		//model.addAttribute("movie", new Movie());
		 model.addAttribute("movie", new Movie());
		 double score = 0.0;
		 boolean isRated = false;
		// System.out.println(movie_name);
		 Movie myMovie = ms.findByName(movie_name);
		 if(myMovie.getTotal_votes() != 0) {
			// System.out.println(myMovie.getTotal_score());
			// System.out.println(myMovie.getTotal_votes());
			 score = (double)myMovie.getTotal_score()/(double)myMovie.getTotal_votes();
			// System.out.println(score);
			 String scores = null;
			 DecimalFormat df = new DecimalFormat();
			 df.setRoundingMode(RoundingMode.DOWN);
			 scores=df.format(score);
			 model.addAttribute("rating", scores);
		 }
		 else{
			model.addAttribute("rating","No Rating yet"); 
		 }
		 String email = (String) session.getAttribute("email");
		 Rating rating = new Rating();
		 rating.setUser_email(email);
		 rating.setMovie_id(myMovie.getMovie_id());
		 isRated=ratingService.findIfAlreadyRated(rating);
		 if(isRated)model.addAttribute("isRated",true);
		 else model.addAttribute("isRated",false);
		 model.addAttribute("movies", myMovie);
		 session.setAttribute("movie", myMovie);
		 return "views/movie";
	 }
	 
	 @PostMapping("/rate")
	 	public String movieRating(HttpSession session,@Valid @ModelAttribute("movie") Movie movie,BindingResult bindingResult,
	 			Model model) {
			if(bindingResult.hasErrors()) {
				return "views/movie";
			}
			Movie myMovie = (Movie) session.getAttribute("movie");
			
			String email = (String) session.getAttribute("email");
			Rating rating = new Rating();
			rating.setMovie_id(myMovie.getMovie_id());
			rating.setUser_email(email);
			
			ratingRepo.save(rating);
			
			
			
			
			myMovie.setTotal_score(movie.getTotal_score()+myMovie.getTotal_score());
			myMovie.setTotal_votes(myMovie.getTotal_votes()+1);
			movierepo.save(myMovie);
			//redirect:/index.html
			return "redirect:/movie/"+myMovie.getName();
			//return "views/movie";
		}
}
